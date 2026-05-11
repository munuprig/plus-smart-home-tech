package ru.yandex.practicum.collector.gRPC.builders.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.gRPC.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@Component
public class SwitchSensorBuilder extends BaseSensorBuilder {
    public SwitchSensorBuilder(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventAvro toAvro(SensorEventProto sensorEvent) {
        SwitchSensorProto switchSensor = sensorEvent.getSwitchSensorEvent();

        return SensorEventAvro.newBuilder()
                .setId(sensorEvent.getId())
                .setHubId(sensorEvent.getHubId())
                .setTimestamp(mapTimestampToInstant(sensorEvent))
                .setPayload(new SwitchSensorAvro(switchSensor.getState()))
                .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }
}
