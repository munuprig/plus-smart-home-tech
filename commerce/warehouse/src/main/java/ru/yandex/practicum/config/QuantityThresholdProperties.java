package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "warehouse.quantity-thresholds")
public class QuantityThresholdProperties {

    private int ended;
    private int enough;
    private int few;

    // getters/setters
}