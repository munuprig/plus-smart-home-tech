package ru.yandex.practicum.collector.gRPC.builders.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.gRPC.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@Component
public class TemperatureSensorBuilder extends BaseSensorBuilder {
    public TemperatureSensorBuilder(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventAvro toAvro(SensorEventProto sensorEvent) {
        TemperatureSensorProto temperatureSensor = sensorEvent.getTemperatureSensorEvent();

        return SensorEventAvro.newBuilder()
                .setId(sensorEvent.getId())
                .setHubId(sensorEvent.getHubId())
                .setTimestamp(mapTimestampToInstant(sensorEvent))
                .setPayload(new TemperatureSensorAvro(temperatureSensor.getTemperatureC(), temperatureSensor.getTemperatureF()))
                .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }
}
