package ru.yandex.practicum.telemetry.collector.mapper.sensor;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.MotionSensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@Component
public class MotionSensorEventMapper extends BaseSensorEventMapper<MotionSensorAvro> {
    @Override
    protected MotionSensorAvro mapToAvroPayload(SensorEventProto event) {
        MotionSensorEventProto sensorEvent = event.getMotionSensorEvent();
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(sensorEvent.getLinkQuality())
                .setMotion(sensorEvent.getMotion())
                .setVoltage(sensorEvent.getVoltage())
                .build();
    }

    @Override
    public SensorEventProto.PayloadCase getSensorEventType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }
}