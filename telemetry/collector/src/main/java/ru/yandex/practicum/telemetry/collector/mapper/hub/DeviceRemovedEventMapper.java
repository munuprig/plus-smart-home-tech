package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;

@Component
public class DeviceRemovedEventMapper extends BaseHubEventMapper<DeviceRemovedEventAvro> {

    @Override
    protected DeviceRemovedEventAvro mapToAvroPayload(HubEvent event) {
        DeviceRemovedEvent hubEvent = (DeviceRemovedEvent) event;

        return DeviceRemovedEventAvro.newBuilder()
                .setId(hubEvent.getId())
                .build();
    }

    @Override
    public HubEventType getHubEventType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
