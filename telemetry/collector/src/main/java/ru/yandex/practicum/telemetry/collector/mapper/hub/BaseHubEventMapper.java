package ru.yandex.practicum.telemetry.collector.mapper.hub;

import java.time.Instant;

import org.apache.avro.specific.SpecificRecordBase;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public abstract class BaseHubEventMapper<T extends SpecificRecordBase> implements HubEventMapper {

    protected abstract T mapToAvroPayload(HubEventProto event);

    @Override
    public HubEventAvro mapToAvro(HubEventProto event) {
        if (!event.getPayloadCase().equals(getHubEventType())) {
            throw new IllegalArgumentException("Неизвестное событие: " + event.getPayloadCase());
        }

        T payload = mapToAvroPayload(event);

        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos()))
                .setPayload(payload)
                .build();
    }
}