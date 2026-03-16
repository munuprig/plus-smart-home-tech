package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioCondition;

import java.util.List;

@Component
public class ScenarioConditionMapper {
    public ScenarioConditionAvro mapToAvro(ScenarioCondition condition) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                .setValue(condition.getValue())
                .build();
    }

    public List<ScenarioConditionAvro> mapToAvro(List<ScenarioCondition> conditions) {
        return conditions.stream().map(this::mapToAvro).toList();
    }
}
