package ru.yandex.practicum.collector.gRPC.producer;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class KafkaEventProducer implements AutoCloseable {

    private final Producer<String, SpecificRecordBase> producer;

    public void send(SpecificRecordBase message, String hubId, Instant timestamp, String topic) {

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, null,
                timestamp.toEpochMilli(), hubId, message);

        producer.send(record);
        producer.flush();
    }

    @Override
    public void close() {
        producer.flush();
        producer.close(Duration.ofSeconds(10));
    }
}
