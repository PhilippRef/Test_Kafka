package org.example.metrics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.metrics.model.MetricDto;
import org.example.metrics.model.MetricDB;
import org.example.metrics.exception.MetricNotFoundException;
import org.example.metrics.repository.MetricRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс для обработки логики метрик
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;

    private static final double HIGH_CPU_TEMP = 90;
    private static final double HIGH_CPU_USAGE = 100;
    private static final double HIGH_MEMORY_USAGE = 95;

    private static final String HIGH_CPU_TEMP_MESSAGE = "Превышение температуры процессора";
    private static final String HIGH_CPU_USAGE_MESSAGE = "Превышение нагрузки процессора";
    private static final String HIGH_MEMORY_USAGE_MESSAGE = "Превышение использования памяти";

    /**
     * Метод для сохранения метрик в БД
     * @param metricDto
     */
    @Transactional
    public void saveMetric(MetricDto metricDto) {

        if (metricDto.getCpuTemperature() > HIGH_CPU_TEMP) {
            MetricDB metricDB = mapToDB(metricDto);

            metricDB.setErrorMessage(HIGH_CPU_TEMP_MESSAGE);

            mapToDto(metricRepository.save(metricDB));
        }
        if (metricDto.getCpuUsage() > HIGH_CPU_USAGE) {
            MetricDB metricDB = mapToDB(metricDto);

            metricDB.setErrorMessage(HIGH_CPU_USAGE_MESSAGE);

            mapToDto(metricRepository.save(metricDB));
        }
        if (metricDto.getMemoryUsage() > HIGH_MEMORY_USAGE) {
            MetricDB metricDB = mapToDB(metricDto);

            metricDB.setErrorMessage(HIGH_MEMORY_USAGE_MESSAGE);

            mapToDto(metricRepository.save(metricDB));
        }
        if (metricDto.getCpuTemperature() <= HIGH_CPU_TEMP ||
                metricDto.getCpuUsage() <= HIGH_CPU_USAGE ||
                metricDto.getMemoryUsage() <= HIGH_MEMORY_USAGE) {

            MetricDB metricDB = mapToDB(metricDto);

            mapToDto(metricRepository.save(metricDB));
        }
    }

    /**
     * Метод для получения метрик по id
     * @param metricId
     * @return MetricDto
     */
    public MetricDto findById(int metricId) {
        Optional<MetricDB> optionalMetricDB = metricRepository.findById(metricId);

        return optionalMetricDB.map(MetricService::mapToDto)
                .orElseThrow(() -> new MetricNotFoundException("Метрика с id = " + metricId + " не найдена"));
    }

    /**
     * Метод для получения всех метрик
     * @return Collection
     */
    public Collection<MetricDto> findAll() {
        return metricRepository.findAll()
                .stream()
                .map(MetricService::mapToDto)
                .toList();
    }

    /**
     * Метод для маппинга метрик из БД в DTO
     * @param metricDB
     * @return MetricDto
     */
    public static MetricDto mapToDto(MetricDB metricDB) {
        MetricDto metricDto = new MetricDto();

        metricDto.setId(metricDB.getId());
        metricDto.setCpuUsage(metricDB.getCpuUsage());
        metricDto.setMemoryUsage(metricDB.getMemoryUsage());
        metricDto.setErrorMessage(metricDB.getErrorMessage());
        metricDto.setCpuTemperature(metricDB.getCpuTemperature());

        return metricDto;
    }

    /**
     * Метод для маппинга метрик из DTO в БД
     * @param metricDto
     * @return MetricDB
     */
    public static MetricDB mapToDB(MetricDto metricDto) {
        MetricDB metricDB = new MetricDB();

        metricDB.setId(metricDto.getId());
        metricDB.setCpuUsage(metricDto.getCpuUsage());
        metricDB.setMemoryUsage(metricDto.getMemoryUsage());
        metricDB.setErrorMessage(metricDto.getErrorMessage());
        metricDB.setCpuTemperature(metricDto.getCpuTemperature());

        return metricDB;
    }
}
