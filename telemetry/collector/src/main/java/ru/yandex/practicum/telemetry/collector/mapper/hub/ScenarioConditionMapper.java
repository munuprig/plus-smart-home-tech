package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.List;

@Component
public class ScenarioConditionMapper {
    public ScenarioConditionAvro mapToAvro(ScenarioConditionProto condition) {
        Object value = null;
        if (condition.getValueCase() == ScenarioConditionProto.ValueCase.INT_VALUE) {
            value = condition.getIntValue();
        }
        if (condition.getValueCase() == ScenarioConditionProto.ValueCase.BOOL_VALUE) {
            value = condition.getBoolValue();
        }

        return ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                .setValue(value)
                .build();
    }

    public List<ScenarioConditionAvro> mapToAvro(List<ScenarioConditionProto> conditions) {
        return conditions.stream().map(this::mapToAvro).toList();
    }
}