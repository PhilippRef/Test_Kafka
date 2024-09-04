package org.example.metrics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.metrics.exception.MetricNotFoundException;
import org.example.metrics.model.MetricDto;
import org.example.metrics.service.KafkaListenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Класс контроллера для KafkaConsumer
 */
@RestController("api/")
@RequiredArgsConstructor
@Tag(name = "KafkaConsumerController", description = "Контроллер для KafkaConsumer")
public class KafkaConsumerController {

    private final KafkaListenerService kafkaListenerService;

    /**
     * Метод get запроса для получения всех метрик.
     * Пример запроса: GET http://localhost:8079/api/metrics
     * Пример ответа: [
     * {
     * "id": 1,
     * "cpuUsage": 20.0,
     * "cpuTemperature": 34.0,
     * "memoryUsage": 44.0,
     * "errorMessage": null
     * },
     * {
     * "id": 2,
     * "cpuUsage": 35.0,
     * "cpuTemperature": 55.0,
     * "memoryUsage": 56.0,
     * "errorMessage": null
     * }
     * ]
     *
     * @return ResponseEntity
     */
    @GetMapping("/metrics")
    @Operation(summary = "Get all metrics", description = "Получение всех метрик")
    public ResponseEntity<Collection<MetricDto>> getAllMetrics() {
        return ResponseEntity.status(HttpStatus.OK).body(kafkaListenerService.getAllMetrics());
    }

    /**
     * Метод get запроса для получения метрики по id.
     * Пример запроса: GET http://localhost:8079/api/metrics/1
     * Пример ответа:
     * {
     * "id": 1,
     * "cpuUsage": 20.0,
     * "cpuTemperature": 30.0,
     * "memoryUsage": 45.0,
     * "errorMessage": null
     * }
     * Пример ответа при отсутствии метрики:
     * Метрика с id = 3 не найдена
     *
     * @param id - идентификатор метрики
     * @return ResponseEntity
     */
    @GetMapping("/metrics/{id}")
    @Operation(summary = "Get metric by id", description = "Получение метрики по id")
    public ResponseEntity<?> getMetricById(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(kafkaListenerService.getMetricById(id));
        } catch (MetricNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
