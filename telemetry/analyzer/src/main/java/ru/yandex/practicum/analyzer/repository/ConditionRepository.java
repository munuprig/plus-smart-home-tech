package ru.yandex.practicum.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.analyzer.model.Condition;
import ru.yandex.practicum.analyzer.model.Scenario;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Long> {

    void deleteByScenario(Scenario scenario);

    List<Condition> findAllByScenario(Scenario scenario);
}
