package ru.yandex.practicum.telemetry.collector.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.config.KafkaClient;
import ru.yandex.practicum.telemetry.collector.mapper.hub.HubEventMapper;
import ru.yandex.practicum.telemetry.collector.mapper.sensor.SensorEventMapper;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CollectorServiceImpl implements CollectorService {
    @Value("${collector.kafka.producer.topics.sensors-events}")
    private String sensorsEventsTopic;
    @Value("${collector.kafka.producer.topics.hubs-events}")
    private String hubsEventsTopic;

    private final KafkaClient kafkaClient;
    private final Map<SensorEventProto.PayloadCase, SensorEventMapper> sensorEventMappers;
    private final Map<HubEventProto.PayloadCase, HubEventMapper> hubEventMappers;

    public CollectorServiceImpl(
            KafkaClient kafkaClient,
            List<SensorEventMapper> sensorEventMapperList,
            List<HubEventMapper> hubEventMapperList) {
        this.kafkaClient = kafkaClient;
        this.sensorEventMappers = sensorEventMapperList.stream()
                .collect(Collectors.toMap(SensorEventMapper::getSensorEventType, Function.identity()));
        this.hubEventMappers = hubEventMapperList.stream()
                .collect(Collectors.toMap(HubEventMapper::getHubEventType, Function.identity()));
    }

    @Override
    public void collectSensorEvent(SensorEventProto event) {
        SensorEventMapper eventMapper;
        if (sensorEventMappers.containsKey(event.getPayloadCase())) {
            eventMapper = sensorEventMappers.get(event.getPayloadCase());
        } else {
            throw new IllegalArgumentException("Нет подходящего mapper'а");
        }

        SensorEventAvro eventAvro = eventMapper.mapToAvro(event);
        kafkaClient.send(
                sensorsEventsTopic,
                eventAvro.getTimestamp().toEpochMilli(),
                eventAvro.getHubId(),
                eventAvro);
    }

    @Override
    public void collectHubEvent(HubEventProto event) {
        HubEventMapper eventMapper;
        if (hubEventMappers.containsKey(event.getPayloadCase())) {
            eventMapper = hubEventMappers.get(event.getPayloadCase());
        } else {
            throw new IllegalArgumentException("Нет подходящего mapper'а");
        }

        HubEventAvro eventAvro = eventMapper.mapToAvro(event);
        kafkaClient.send(
                hubsEventsTopic,
                eventAvro.getTimestamp().toEpochMilli(),
                eventAvro.getHubId(),
                eventAvro);
    }
}