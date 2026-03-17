package ru.yandex.practicum.telemetry.collector.mapper.hub;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;

public interface HubEventMapper {
    HubEventType getHubEventType();

    HubEventAvro mapToAvro(HubEvent event);
}
