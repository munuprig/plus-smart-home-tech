package ru.yandex.practicum.telemetry.collector.mapper.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.enums.HubEventType;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventMapper extends BaseHubEventMapper<ScenarioAddedEventAvro> {
    private final ScenarioConditionMapper conditionMapper;
    private final DeviceActionMapper actionMapper;

    @Override
    protected ScenarioAddedEventAvro mapToAvroPayload(HubEvent event) {
        ScenarioAddedEvent hubEvent = (ScenarioAddedEvent) event;

        return ScenarioAddedEventAvro.newBuilder()
                .setName(hubEvent.getName())
                .setConditions(conditionMapper.mapToAvro(hubEvent.getConditions()))
                .setActions(actionMapper.mapToAvro(hubEvent.getActions()))
                .build();
    }

    @Override
    public HubEventType getHubEventType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
