package org.example.metrics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metrics.model.MetricDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс для обработки логики при отправке сообщений
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaSenderService {

    private final KafkaTemplate<String, MetricDto> kafkaTemplate;

    private static final String TOPIC = "metrics-topic";

    /**
     * Метод для отправки метрики в Kafka
     *
     * @param metricDto
     */
    public void sendMetric(MetricDto metricDto) {
        log.info("Sending metric to Kafka: {}", metricDto);

        kafkaTemplate.send(TOPIC, metricDto);
    }
}
