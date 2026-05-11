package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

@Component
public class DeviceAddedEventMapper extends BaseHubEventMapper<DeviceAddedEventAvro> {

    @Override
    protected DeviceAddedEventAvro mapToAvroPayload(HubEventProto event) {
        DeviceAddedEventProto hubEvent = event.getDeviceAdded();

        return DeviceAddedEventAvro.newBuilder()
                .setId(hubEvent.getId())
                .setType(DeviceTypeAvro.valueOf(hubEvent.getType().name()))
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getHubEventType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }
}