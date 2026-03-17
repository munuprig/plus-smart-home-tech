package ru.yandex.practicum.telemetry.collector.service;

import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;

public interface CollectorService {
    void collectSensorEvent(SensorEvent event);

    void collectHubEvent(HubEvent event);
}
