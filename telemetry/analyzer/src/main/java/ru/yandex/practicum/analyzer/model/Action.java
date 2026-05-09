package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;

@Entity
@Getter
@Builder
@Table(name = "actions")
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTable(name = "scenario_actions", pkJoinColumns = @PrimaryKeyJoinColumn(name = "action_id"))
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionTypeAvro type;

    private Integer value;

    @ManyToOne
    @JoinColumn(name = "scenario_id", table = "scenario_actions")
    private Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "sensor_id", table = "scenario_actions")
    private Sensor sensor;
}
