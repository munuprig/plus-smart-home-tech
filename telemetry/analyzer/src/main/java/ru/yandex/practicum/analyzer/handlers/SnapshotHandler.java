package ru.yandex.practicum.analyzer.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.client.HubRouterClient;
import ru.yandex.practicum.analyzer.model.Condition;
import ru.yandex.practicum.analyzer.model.Scenario;
import ru.yandex.practicum.analyzer.repository.ActionRepository;
import ru.yandex.practicum.analyzer.repository.ConditionRepository;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotHandler {

    private final ConditionRepository conditionRepository;
    private final ScenarioRepository scenarioRepository;
    private final ActionRepository actionRepository;
    private final HubRouterClient hubRouterClient;

    public void buildSnapshot(SensorsSnapshotAvro sensorsSnapshot) {
        Map<String, SensorStateAvro> sensorStateMap = sensorsSnapshot.getSensorsState();
        List<Scenario> scenarios = scenarioRepository.findByHubId(sensorsSnapshot.getHubId());
        scenarios.stream()
                .filter(scenario -> handleScenario(scenario, sensorStateMap))
                .forEach(scenario -> {
                    sendScenarioActions(scenario);
                });
    }

    private boolean handleScenario(Scenario scenario, Map<String, SensorStateAvro> sensorStateMap) {
        List<Condition> conditions = conditionRepository.findAllByScenario(scenario);
        return conditions.stream().noneMatch(condition -> !checkCondition(condition, sensorStateMap));
    }

    private boolean checkCondition(Condition condition, Map<String, SensorStateAvro> sensorStateMap) {
        String sensorId = condition.getSensor().getId();
        SensorStateAvro sensorState = sensorStateMap.get(sensorId);
        if (sensorState == null) {
            return false;
        }

        switch (condition.getType()) {
            case LUMINOSITY -> {
                LightSensorAvro lightSensor = (LightSensorAvro) sensorState.getData();
                return handleOperation(condition, lightSensor.getLuminosity());
            }
            case TEMPERATURE -> {
                ClimateSensorAvro temperatureSensor = (ClimateSensorAvro) sensorState.getData();
                return handleOperation(condition, temperatureSensor.getTemperatureC());
            }
            case MOTION -> {
                MotionSensorAvro motionSensor = (MotionSensorAvro) sensorState.getData();
                return handleOperation(condition, motionSensor.getMotion() ? 1 : 0);
            }
            case SWITCH -> {
                SwitchSensorAvro switchSensor = (SwitchSensorAvro) sensorState.getData();
                return handleOperation(condition, switchSensor.getState() ? 1 : 0);
            }
            case CO2LEVEL -> {
                ClimateSensorAvro climateSensor = (ClimateSensorAvro) sensorState.getData();
                return handleOperation(condition, climateSensor.getCo2Level());
            }
            case HUMIDITY -> {
                ClimateSensorAvro climateSensor = (ClimateSensorAvro) sensorState.getData();
                return handleOperation(condition, climateSensor.getHumidity());
            }
            case null -> {
                return false;
            }
        }
    }

    private Boolean handleOperation(Condition condition, Integer currentValue) {
        ConditionOperationAvro conditionOperation = condition.getOperation();
        Integer targetValue = condition.getValue();

        switch (conditionOperation) {
            case EQUALS -> {
                return targetValue == currentValue;
            }
            case LOWER_THAN -> {
                return currentValue < targetValue;
            }
            case GREATER_THAN -> {
                return currentValue > targetValue;
            }
            case null -> {
                return null;
            }
        }
    }

    private void sendScenarioActions(Scenario scenario) {
        actionRepository.findAllByScenario(scenario).forEach(hubRouterClient::sendAction);
    }
}
