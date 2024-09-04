package org.example.metrics.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.metrics.model.MetricDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для конфигурации KafkaProducer
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     * Bean для создания ProducerFactory. Здесь указано, каким образом будут обрабатываться исходящие сообщения.
     * @return ProducerFactory
     */
    @Bean
    public ProducerFactory<String, MetricDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Bean для создания KafkaTemplate.
     * @return KafkaTemplate
     */
    @Bean
    public KafkaTemplate<String, MetricDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Bean для создания топика
     * @return
     */
    @Bean
    public NewTopic topic1() {
        return new NewTopic("metrics-topic", 3, (short) 2);
    }
}
