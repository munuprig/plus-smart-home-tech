package ru.yandex.practicum.analyzer.handlers;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventHandler {

    void handle(HubEventAvro event);

    String getMessageType();
}
