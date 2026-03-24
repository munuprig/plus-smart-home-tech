package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;

import java.util.List;

@Component
public class DeviceActionMapper {
    public DeviceActionAvro mapToAvro(DeviceActionProto action) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(ActionTypeAvro.valueOf(action.getType().name()))
                .setValue(action.getValue())
                .build();
    }

    public List<DeviceActionAvro> mapToAvro(List<DeviceActionProto> actions) {
        return actions.stream().map(this::mapToAvro).toList();
    }
}