package org.example.metrics.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс для хранения данных о метриках в БД
 */

@Entity
@Table(name = "metrics")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MetricDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cpu_usage", nullable = false)
    private double cpuUsage;

    @Column(name = "cpu_temperature", nullable = false)
    private double cpuTemperature;

    @Column(name = "memory_usage", nullable = false)
    private double memoryUsage;

    @Column(name = "error_message")
    private String errorMessage;
}
