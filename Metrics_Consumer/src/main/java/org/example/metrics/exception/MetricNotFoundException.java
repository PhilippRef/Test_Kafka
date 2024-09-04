package org.example.metrics.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Класс для вызова исключения при отсутствии запрашиваемой метрики
 */

@Slf4j
public class MetricNotFoundException extends RuntimeException {

    public MetricNotFoundException() {
    }

    public MetricNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
