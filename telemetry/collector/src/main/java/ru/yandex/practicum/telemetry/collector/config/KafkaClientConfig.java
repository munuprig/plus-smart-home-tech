package ru.yandex.practicum.telemetry.collector.config;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.Future;

@Configuration
public class KafkaClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "collector.kafka.producer.properties")
    public Properties kafkaProducerProperties() {
        return new Properties();
    }

    @Bean
    public Producer<String, SpecificRecordBase> kafkaProducer(Properties kafkaProducerProperties) {
        return new KafkaProducer<>(kafkaProducerProperties);
    }

    @Bean
    public KafkaClient getKafkaClient(Producer<String, SpecificRecordBase> kafkaProducer) {
        return new KafkaClient() {

            @Override
            public void send(String topic, Long timestamp, String hubId, SpecificRecordBase event) {
                kafkaProducer.send(new ProducerRecord<>(topic, null, timestamp, hubId, event));
                kafkaProducer.flush();
            }

            @Override
            public void close() {
                try {
                    kafkaProducer.flush();
                } finally {
                    kafkaProducer.close(Duration.ofSeconds(10));
                }
            }

            @Override
            public void flush() { kafkaProducer.flush(); }
        };
    }
}