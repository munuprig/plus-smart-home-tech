package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MotionSensorConfig {

    private String id;

    private MinMaxConfig linkQuality;

    private MinMaxConfig voltage;
}
