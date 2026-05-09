package ru.yandex.practicum.collector.gRPC.builders.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.gRPC.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class ClimateSensorBuilder extends BaseSensorBuilder {
    public ClimateSensorBuilder(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventAvro toAvro(SensorEventProto sensorEvent) {
        ClimateSensorProto climateSensor = sensorEvent.getClimateSensorEvent();

        return SensorEventAvro.newBuilder()
                .setId(sensorEvent.getId())
                .setHubId(sensorEvent.getHubId())
                .setTimestamp(mapTimestampToInstant(sensorEvent))
                .setPayload(new ClimateSensorAvro(climateSensor.getTemperatureC(), climateSensor.getHumidity(), climateSensor.getCo2Level()))
                .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }
}
