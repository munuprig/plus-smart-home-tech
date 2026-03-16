package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;

@Component
public class ScenarioRemovedEventMapper extends BaseHubEventMapper<ScenarioRemovedEventAvro> {
    @Override
    protected ScenarioRemovedEventAvro mapToAvroPayload(HubEvent event) {
        ScenarioRemovedEvent hubEvent = (ScenarioRemovedEvent) event;

        return ScenarioRemovedEventAvro.newBuilder()
                .setName(hubEvent.getName())
                .build();
    }

    @Override
    public HubEventType getHubEventType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
