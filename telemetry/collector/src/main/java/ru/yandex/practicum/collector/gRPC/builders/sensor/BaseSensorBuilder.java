package ru.yandex.practicum.collector.gRPC.builders.sensor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.collector.gRPC.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseSensorBuilder implements SensorEventBuilder {

    private final KafkaEventProducer producer;

    @Value("${topic.telemetry-sensors}")

    private String topic;

    @Override
    public void builder(SensorEventProto event) {
        var contract = toAvro(event);
        log.info("Отправляем событие сенсора {}", contract);
        producer.send(contract, event.getHubId(), mapTimestampToInstant(event), topic);
    }

    public Instant mapTimestampToInstant(SensorEventProto event) {
        return Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos());
    }

    public abstract SensorEventAvro toAvro(SensorEventProto sensorEvent);

    public abstract SensorEventProto.PayloadCase getMessageType();
}
