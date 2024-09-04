package org.example.metrics.model;

import lombok.*;

/**
 * Класс DTO для работы с метриками
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MetricDto {

    private Integer id;
    private double cpuUsage;
    private double cpuTemperature;
    private double memoryUsage;
    private String errorMessage;
}
