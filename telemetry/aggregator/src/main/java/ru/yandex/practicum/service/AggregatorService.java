package ru.yandex.practicum.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;

public interface AggregatorService {
    void aggregationSnapshot(Producer<String, SpecificRecordBase> producer, SpecificRecordBase sensorEventAvro);
}