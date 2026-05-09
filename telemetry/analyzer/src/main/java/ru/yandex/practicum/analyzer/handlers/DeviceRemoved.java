package ru.yandex.practicum.analyzer.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceRemoved implements HubEventHandler {

    private final SensorRepository sensorRepository;

    @Override
    @Transactional
    public void handle(HubEventAvro hubEvent) {
        DeviceRemovedEventAvro deviceRemovedEvent = (DeviceRemovedEventAvro) hubEvent.getPayload();
        sensorRepository.deleteByIdAndHubId(deviceRemovedEvent.getId(), hubEvent.getHubId());
    }

    @Override
    public String getMessageType() {
        return DeviceRemovedEventAvro.class.getSimpleName();
    }
}
