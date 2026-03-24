package ru.yandex.practicum.kafka.deserializer;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public class SnapshotEventDeserializer extends BaseAvroDeserializer<SensorsSnapshotAvro> {
    public SnapshotEventDeserializer() {
        super(SensorsSnapshotAvro.getClassSchema());
    }
}