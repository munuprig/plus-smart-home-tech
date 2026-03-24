package ru.yandex.practicum.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AggregatorServiceImpl implements AggregatorService {
    @Value("${collector.kafka.topics.snapshots-events}")
    private String snapshotsEventsTopic;
    private Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    @Override
    public void aggregationSnapshot(Producer<String, SpecificRecordBase> producer, SpecificRecordBase sensorEventAvro) {
        SensorEventAvro event = (SensorEventAvro) sensorEventAvro;
        Optional<SensorsSnapshotAvro> snapshotOpt = updateState(event);
        if (snapshotOpt.isPresent()) {
            SensorsSnapshotAvro snapshot = snapshotOpt.get();
            producer.send(new ProducerRecord<>(
                    snapshotsEventsTopic,
                    null,
                    snapshot.getTimestamp().toEpochMilli(),
                    snapshot.getHubId(),
                    snapshot));
        }
    }

    private Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
        if (!snapshots.containsKey(event.getHubId())) {
            SensorsSnapshotAvro snapshot = new SensorsSnapshotAvro();
            snapshot.setSensorsState(new HashMap<>());
            snapshots.put(event.getHubId(), snapshot);
        }

        SensorsSnapshotAvro snapshot = snapshots.get(event.getHubId());
        if (snapshot.getSensorsState().containsKey(event.getId())) {
            SensorStateAvro oldState = snapshot.getSensorsState().get(event.getId());
            if (oldState.getTimestamp().isAfter(event.getTimestamp())
                    || oldState.getData().equals(event.getPayload())) {
                return Optional.empty();
            }
        }

        SensorStateAvro sensorState = SensorStateAvro.newBuilder()
                .setTimestamp(event.getTimestamp())
                .setData(event.getPayload())
                .build();

        snapshot.setHubId(event.getHubId());
        snapshot.setTimestamp(Instant.now());
        snapshot.getSensorsState().put(event.getId(), sensorState);
        snapshots.put(snapshot.getHubId(), snapshot);
        return Optional.of(snapshot);
    }
}