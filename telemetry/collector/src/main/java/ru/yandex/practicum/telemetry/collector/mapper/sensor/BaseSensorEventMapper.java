package ru.yandex.practicum.telemetry.collector.mapper.sensor;

import java.time.Instant;

import org.apache.avro.specific.SpecificRecordBase;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

public abstract class BaseSensorEventMapper<T extends SpecificRecordBase> implements SensorEventMapper {

    protected abstract T mapToAvroPayload(SensorEventProto event);

    @Override
    public SensorEventAvro mapToAvro(SensorEventProto event) {
        if (!event.getPayloadCase().equals(getSensorEventType())) {
            throw new IllegalArgumentException("Неизвестное событие: " + event.getPayloadCase());
        }

        T payload = mapToAvroPayload(event);

        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos()))
                .setPayload(payload)
                .build();
    }
}