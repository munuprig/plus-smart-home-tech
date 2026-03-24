package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LightSensorConfig {

    private String id;

    private MinMaxConfig luminosity;

    private MinMaxConfig linkQuality;
}
