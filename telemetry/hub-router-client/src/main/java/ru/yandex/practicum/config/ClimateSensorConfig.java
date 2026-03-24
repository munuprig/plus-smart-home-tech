package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClimateSensorConfig {

    private String id;

    private MinMaxConfig temperature;

    private MinMaxConfig humidity;

    private MinMaxConfig co2Level;
}
