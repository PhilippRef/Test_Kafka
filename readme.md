Приложение для мониторинга метрик работы системы.

Используемые технологии: Spring Boot, Spring Data Jpa, Maven, Kafka, PostgeSQL, Docker, liquibase.

Для запуска приложения необходимо выполнить следующие действия:
1. Создать БД в PostgeSQL с именем metrics.
2. Таблицы в БД создаются с помощью liquibase.
3. В соответствии с docker-compose.yaml запустить контейнеры: zookeeper и kafka.
4. Запустить MetricsProducerApplication.
5. Запустить MetricsConsumerApplication.

В приложении имеется краткая документация с использованием Swagger.
Информация доступа по адресу: http://localhost:8080/swagger-ui/index.html.

Для добавления метрик в Kafka необходимо выполнить POST-запрос на адрес: http://localhost:9090/metrics с примерным телом запроса:

{

"cpuUsage": "54",

"memoryUsage": "75",

"cpuTemperature": "67"

}

В consumer реализованы сценарии записи ошибки в зависимости от значений передаваемых параметров. В случае превышении следующих значений в БД будут записываться соответствующие сообщения об ошибках:

{

"cpuUsage": "100",

"memoryUsage": "95",

"cpuTemperature": "90"

}