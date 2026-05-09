package ru.yandex.practicum.collector.gRPC.builders.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import ru.yandex.practicum.collector.gRPC.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseHubBuilder implements HubEventBuilder {

    private final KafkaEventProducer producer;

    @Value("${topic.telemetry-hubs}")

    private String topic;

    @Override
    public void builder(HubEventProto event) {
        var contract = toAvro(event);
        log.info("Отправляем событие хаба {}", contract);
        producer.send(toAvro(event), event.getHubId(), mapTimestampToInstant(event), topic);
    }

    public Instant mapTimestampToInstant(HubEventProto event) {
        return Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos());
    }

    public abstract HubEventAvro toAvro(HubEventProto hubEvent);

    public abstract HubEventProto.PayloadCase getMessageType();
}
