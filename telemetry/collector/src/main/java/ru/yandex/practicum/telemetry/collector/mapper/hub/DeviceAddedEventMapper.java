package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;

@Component
public class DeviceAddedEventMapper extends BaseHubEventMapper<DeviceAddedEventAvro> {

    @Override
    protected DeviceAddedEventAvro mapToAvroPayload(HubEvent event) {
        DeviceAddedEvent hubEvent = (DeviceAddedEvent) event;

        return DeviceAddedEventAvro.newBuilder()
                .setId(hubEvent.getId())
                .setType(DeviceTypeAvro.valueOf(hubEvent.getDeviceType().name()))
                .build();
    }

    @Override
    public HubEventType getHubEventType() {
        return HubEventType.DEVICE_ADDED;
    }
}
