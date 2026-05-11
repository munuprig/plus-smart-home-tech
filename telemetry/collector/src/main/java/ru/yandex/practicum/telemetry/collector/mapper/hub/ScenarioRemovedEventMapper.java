package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
public class ScenarioRemovedEventMapper extends BaseHubEventMapper<ScenarioRemovedEventAvro> {
    @Override
    protected ScenarioRemovedEventAvro mapToAvroPayload(HubEventProto event) {
        ScenarioRemovedEventProto hubEvent = event.getScenarioRemoved();

        return ScenarioRemovedEventAvro.newBuilder()
                .setName(hubEvent.getName())
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getHubEventType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }
}