package org.example.metrics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metrics.model.MetricDto;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Сервисный класс для обработки логики при получении сообщений
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final MetricService metricService;

    private static final String TOPIC_NAME = "metrics-topic";
    private static final String GROUP_ID = "metrics-group";

    /**
     * Метод для получения всех метрик
     * @return Collection
     */
    public Collection<MetricDto> getAllMetrics() {
        log.info("Get all metrics");

        return metricService.findAll();
    }

    /**
     * Метод для получения метрики по id
     *
     * @param metricId
     * @return MetricDto
     */
    public MetricDto getMetricById(int metricId) {
        log.info("Get metric by id: {}", metricId);

        return metricService.findById(metricId);
    }

    /**
     * Метод для сохранения метрики из Kafka в БД
     * @param metricDto
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID,
            containerFactory = "metricListener")
    public void saveMetric(MetricDto metricDto) {
        log.info("Received message: {}", metricDto);

        metricService.saveMetric(metricDto);
    }

    /**
     * Метод для перехвата сообщений по умолчанию
     * @param message
     */
    @KafkaHandler(isDefault = true)
    public void listenDefault(String message) {
        log.info("Kafka default handler: {}", message);
    }

}
