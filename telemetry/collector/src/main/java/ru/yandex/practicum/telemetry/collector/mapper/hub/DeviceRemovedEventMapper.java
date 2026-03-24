package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Component
public class DeviceRemovedEventMapper extends BaseHubEventMapper<DeviceRemovedEventAvro> {

    @Override
    protected DeviceRemovedEventAvro mapToAvroPayload(HubEventProto event) {
        DeviceRemovedEventProto hubEvent = event.getDeviceRemoved();

        return DeviceRemovedEventAvro.newBuilder()
                .setId(hubEvent.getId())
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getHubEventType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }
}