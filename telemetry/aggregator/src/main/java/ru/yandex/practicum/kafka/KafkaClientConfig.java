package ru.yandex.practicum.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Properties;

@Configuration
public class KafkaClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "collector.kafka.producer.properties")
    public Properties kafkaProducerProperties() {
        return new Properties();
    }

    @Bean
    @ConfigurationProperties(prefix = "collector.kafka.consumer.properties")
    public Properties kafkaConsumerProperties() {
        return new Properties();
    }

    @Bean
    KafkaClient getKafkaClient() {
        return new KafkaClient() {
            private Producer<String, SpecificRecordBase> kafkaProducer;
            private Consumer<String, SpecificRecordBase> kafkaConsumer;

            @Override
            public Producer<String, SpecificRecordBase> getProducer() {
                kafkaProducer = new KafkaProducer<>(kafkaProducerProperties());
                return kafkaProducer;
            }

            @Override
            public Consumer<String, SpecificRecordBase> getConsumer() {
                kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties());
                return kafkaConsumer;
            }

            @Override
            public void close() {
                try {
                    kafkaProducer.flush();
                    kafkaConsumer.commitSync();
                } finally {
                    kafkaProducer.close(Duration.ofSeconds(10));
                    kafkaConsumer.close();
                }
            }
        };
    }
}