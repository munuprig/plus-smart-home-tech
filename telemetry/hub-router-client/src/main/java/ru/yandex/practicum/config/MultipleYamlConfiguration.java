package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "sensor")
public class MultipleYamlConfiguration {

    List<ClimateSensorConfig> climateSensors;

    List<LightSensorConfig> lightSensors;

    List<MotionSensorConfig> motionSensors;

    List<SwitchSensorConfig> switchSensors;

    List<TemperatureSensorConfig> temperatureSensors;
}


