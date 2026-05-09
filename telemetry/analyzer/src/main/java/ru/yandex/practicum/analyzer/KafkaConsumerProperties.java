package ru.yandex.practicum.analyzer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerProperties {

    private final Environment environment;

    @Bean
    public Consumer<String, HubEventAvro> getHubEventProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, environment.getProperty("spring.kafka.consumer.client-id"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.bootstrap-servers"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                environment.getProperty("spring.kafka.consumer.key-deserializer"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                environment.getProperty("spring.kafka.consumer.value-deserializer"));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                environment.getProperty("spring.kafka.consumer.enable-auto-commit"));

        return new KafkaConsumer<>(properties);
    }

    @Bean
    public Consumer<String, SensorsSnapshotAvro> getSnapshotProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, environment.getProperty("spring.kafka.consumer.snapshots-client-id"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.snapshots-group-id"));
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.bootstrap-servers"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                environment.getProperty("spring.kafka.consumer.key-deserializer"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                environment.getProperty("spring.kafka.consumer.snapshots-deserializer"));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                environment.getProperty("spring.kafka.consumer.enable-auto-commit"));

        return new KafkaConsumer<>(properties);
    }
}
