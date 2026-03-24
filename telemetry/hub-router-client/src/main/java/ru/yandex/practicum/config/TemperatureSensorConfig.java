package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureSensorConfig {

    private String id;

    private MinMaxConfig temperature;
}
