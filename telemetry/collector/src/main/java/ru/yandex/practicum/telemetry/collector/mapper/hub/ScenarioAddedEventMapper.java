package ru.yandex.practicum.telemetry.collector.mapper.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventMapper extends BaseHubEventMapper<ScenarioAddedEventAvro> {
    private final ScenarioConditionMapper conditionMapper;
    private final DeviceActionMapper actionMapper;

    @Override
    protected ScenarioAddedEventAvro mapToAvroPayload(HubEventProto event) {
        ScenarioAddedEventProto hubEvent = event.getScenarioAdded();

        return ScenarioAddedEventAvro.newBuilder()
                .setName(hubEvent.getName())
                .setConditions(conditionMapper.mapToAvro(hubEvent.getConditionList()))
                .setActions(actionMapper.mapToAvro(hubEvent.getActionList()))
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getHubEventType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }
}