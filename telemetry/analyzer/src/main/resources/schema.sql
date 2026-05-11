
CREATE TABLE IF NOT EXISTS scenarios (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    hub_id VARCHAR(100),
    name VARCHAR(100),
    UNIQUE(hub_id, name)
);


CREATE TABLE IF NOT EXISTS sensors (
    id VARCHAR(100) PRIMARY KEY,
    hub_id VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS conditions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(100),
    operation VARCHAR(100),
    value INTEGER
);


CREATE TABLE IF NOT EXISTS actions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(100),
    value INTEGER
);

-- датчик и условие активации сценария
CREATE TABLE IF NOT EXISTS scenario_conditions (
    scenario_id BIGINT REFERENCES scenarios(id),
    sensor_id VARCHAR(100) REFERENCES sensors(id),
    condition_id BIGINT REFERENCES conditions(id),
    PRIMARY KEY (scenario_id, sensor_id, condition_id)
);

--датчик и действие, которое нужно выполнить при активации сценария
CREATE TABLE IF NOT EXISTS scenario_actions (
    scenario_id BIGINT REFERENCES scenarios(id),
    sensor_id VARCHAR(100) REFERENCES sensors(id),
    action_id BIGINT REFERENCES actions(id),
    PRIMARY KEY (scenario_id, sensor_id, action_id)
);

--сценарий и датчик работают с одним и тем же хабом
CREATE OR REPLACE FUNCTION check_hub_id()
RETURNS TRIGGER AS
'
BEGIN
IF (SELECT hub_id FROM scenarios WHERE id = NEW.scenario_id) != (SELECT hub_id FROM sensors WHERE id = NEW.sensor_id) THEN
    RAISE EXCEPTION ''Hub IDs do not match for scenario_id % and sensor_id %'', NEW.scenario_id, NEW.sensor_id;
END IF;
RETURN NEW;
END;
'
LANGUAGE plpgsql;

--  триггер, проверяющий корректные сценарий и датчик
CREATE OR REPLACE TRIGGER tr_bi_scenario_conditions_hub_id_check
BEFORE INSERT ON scenario_conditions
FOR EACH ROW
EXECUTE FUNCTION check_hub_id();

--  триггер, проверяющий корректные сценарий и датчик
CREATE OR REPLACE TRIGGER tr_bi_scenario_actions_hub_id_check
BEFORE INSERT ON scenario_actions
FOR EACH ROW
EXECUTE FUNCTION check_hub_id();