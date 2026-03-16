package ru.yandex.practicum.telemetry.collector.mapper.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.SwitchSensorEvent;

@Component
public class SwitchSensorEventMapper extends BaseSensorEventMapper<SwitchSensorAvro> {
    @Override
    protected SwitchSensorAvro mapToAvroPayload(SensorEvent event) {
        SwitchSensorEvent sensorEvent = (SwitchSensorEvent) event;
        return SwitchSensorAvro.newBuilder()
                .setState(sensorEvent.getState())
                .build();
    }

    @Override
    public SensorEventType getSensorEventType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
