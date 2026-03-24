package ru.yandex.practicum.telemetry.collector.config;

import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaClient extends AutoCloseable {
    void send(String topic, Long timestamp, String hubId, SpecificRecordBase event);

    default void flush(){};

}