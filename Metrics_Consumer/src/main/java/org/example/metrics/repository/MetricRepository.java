package org.example.metrics.repository;

import org.example.metrics.model.MetricDB;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с данными о метриках
 */
public interface MetricRepository extends JpaRepository<MetricDB, Integer> {
}
