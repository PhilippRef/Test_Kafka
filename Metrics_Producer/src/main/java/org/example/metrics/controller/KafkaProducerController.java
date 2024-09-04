package org.example.metrics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.metrics.model.MetricDto;
import org.example.metrics.service.KafkaSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс контроллера для KafkaProducer
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "KafkaProducerController", description = "Контроллер для KafkaProducer")
public class KafkaProducerController {

    private final KafkaSenderService kafkaSenderService;

    /**
     * Метод post запроса для создания метрики
     * Пример запроса: POST http://localhost:9090/metrics в формате Json.
     * {
     *     "cpuUsage": "45",
     *     "memoryUsage": "60",
     *     "cpuTemperature": "70"
     * }
     * Пример ответа:
     * Data sent successfully: MetricDto(id=null, cpuUsage=45.0, cpuTemperature=60.0, memoryUsage=70.0, errorMessage=null)
     * @param metricDto
     * @return ResponseEntity
     */
    @PostMapping("/metrics")
    @Operation(summary = "Send metric", description = "Создание метрик")
    public ResponseEntity<?> sendMetric(@RequestBody MetricDto metricDto) {
        kafkaSenderService.sendMetric(metricDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Data sent successfully: " + metricDto);
    }
}
