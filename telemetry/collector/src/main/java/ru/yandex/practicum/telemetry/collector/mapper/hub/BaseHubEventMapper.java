package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;

public abstract class BaseHubEventMapper<T extends SpecificRecordBase> implements HubEventMapper {

    protected abstract T mapToAvroPayload(HubEvent event);

    @Override
    public HubEventAvro mapToAvro(HubEvent event) {
        if (!event.getType().equals(getHubEventType())) {
            throw new IllegalArgumentException("Неизвестное событие: " + event.getType());
        }

        T payload = mapToAvroPayload(event);

        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();
    }
}
