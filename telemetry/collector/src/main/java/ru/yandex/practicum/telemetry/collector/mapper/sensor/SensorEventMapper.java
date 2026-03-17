package ru.yandex.practicum.telemetry.collector.mapper.sensor;

import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;

public interface SensorEventMapper {
    SensorEventType getSensorEventType();

    SensorEventAvro mapToAvro(SensorEvent event);
}
