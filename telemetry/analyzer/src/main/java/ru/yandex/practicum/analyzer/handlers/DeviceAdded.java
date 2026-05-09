package ru.yandex.practicum.analyzer.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAdded implements HubEventHandler {

    private final SensorRepository sensorRepository;

    @Override
    @Transactional
    public void handle(HubEventAvro hubEvent) {
        sensorRepository.save(buildToSensor(hubEvent));
    }

    @Override
    public String getMessageType() {
        return DeviceAddedEventAvro.class.getSimpleName();
    }

    private Sensor buildToSensor(HubEventAvro hubEvent) {
        DeviceAddedEventAvro deviceAddedEvent = (DeviceAddedEventAvro) hubEvent.getPayload();

        return Sensor.builder()
                .id(deviceAddedEvent.getId())
                .hubId(hubEvent.getHubId())
                .build();
    }
}
