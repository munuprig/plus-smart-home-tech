package ru.yandex.practicum;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.KafkaClient;
import ru.yandex.practicum.service.AggregatorService;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class AggregationStarter {

    private final Producer<String, SpecificRecordBase> producer;
    private final Consumer<String, SpecificRecordBase> consumer;
    private final AggregatorService aggregatorService;

    @Value("${collector.kafka.topics.sensors-events}")
    private String sensorsEventsTopic;
    @Value("${collector.kafka.topics.snapshots-events}")
    private String snapshotsEventsTopic;
    private static final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);

    public AggregationStarter(KafkaClient kafkaClient, AggregatorService aggregatorService) {
        this.producer = kafkaClient.getProducer();
        this.consumer = kafkaClient.getConsumer();
        this.aggregatorService = aggregatorService;
    }

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
        try {
            consumer.subscribe(List.of(sensorsEventsTopic));
            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(CONSUME_ATTEMPT_TIMEOUT);
                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    log.info("Передаем сообщение для агрегации");
                    aggregatorService.aggregationSnapshot(producer, record.value());
                }
                consumer.commitAsync();
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close(Duration.ofSeconds(5));
            }
        }
    }
}