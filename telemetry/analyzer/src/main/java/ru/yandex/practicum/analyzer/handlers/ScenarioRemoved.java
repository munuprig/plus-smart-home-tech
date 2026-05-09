package ru.yandex.practicum.analyzer.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Scenario;
import ru.yandex.practicum.analyzer.repository.*;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioRemoved implements HubEventHandler {

    private final ScenarioRepository scenarioRepository;
    private final ConditionRepository conditionRepository;
    private final ActionRepository actionRepository;

    @Override
    @Transactional
    public void handle(HubEventAvro hubEvent) {
        ScenarioRemovedEventAvro scenarioRemovedEvent = (ScenarioRemovedEventAvro) hubEvent.getPayload();
        Optional<Scenario> scenarioOpt = scenarioRepository.findByHubIdAndName(hubEvent.getHubId(), scenarioRemovedEvent.getName());

        if (scenarioOpt.isPresent()) {
            Scenario scenario = scenarioOpt.get();
            conditionRepository.deleteByScenario(scenario);
            actionRepository.deleteByScenario(scenario);
            scenarioRepository.delete(scenario);
        } else {
            log.info("Сценарий не найден.");
        }
    }

    @Override
    public String getMessageType() {
        return ScenarioRemovedEventAvro.class.getSimpleName();
    }
}

