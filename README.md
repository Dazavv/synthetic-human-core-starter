# Synthetic Human Core Starter (Project Bishop)

## Назначение

Этот модуль `synthetic-human-core-starter` является ядром для управления поведением синтетических людей, разрабатываемых компанией Weyland-Yutani. Он предназначен для повторного использования и подключения в проекты, такие как `bishop-prototype`, и обеспечивает стандартную реализацию для работы с командами, аудитом, мониторингом и обработкой ошибок.

## 🚀 Основной функционал

### Модуль приёма и исполнения команд

Команды поступают в формате с полями:

* `description`: описание задания (до 1000 символов)
* `priority`: `CRITICAL` / `COMMON`
* `author`: до 100 символов
* `time`: ISO8601

Особенности:

* `CRITICAL` выполняются немедленно;
* `COMMON` — ставятся в очередь (ограничена по размеру);
* Переполнение очереди — ошибка с понятным описанием;
* Исполнение = логирование команды.

### Аудит: `@WeylandWatchingYou`

Аннотация, позволяющая логировать действия методов с помощью AOP:

* Название метода
* Аргументы
* Результат / ошибка

Поддержка двух режимов:

* Kafka topic (по конфигурации)
* Консольный вывод (по умолчанию)

### Мониторинг загрузки

С помощью Micrometer и Actuator:

* Количество задач в очереди
* Количество выполненных задач на каждого автора

### Обработка ошибок

Используется `@ControllerAdvice` и `@ExceptionHandler`:

* Общий формат ошибок (время, код, сообщение, причина)
* Соответствие HTTP-статусам (400, 500 и т.д.)

## Пример использования

```java
@Component
public class MyController {
    private final CommandService commandService;

    public MyController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/command")
    public void runCommand(@RequestBody Command command) {
        commandService.processCommand(command);
    }
}
```

## Технологии

* Java 17+
* Spring Boot 3
* Spring AOP
* ThreadPoolExecutor
* Kafka
* Micrometer + Prometheus
* SLF4J Logging

## Сборка и публикация

```bash
./mvnw clean install
```

Для подключения:

```xml
<dependency>
  <groupId>org.example</groupId>
  <artifactId>synthetic-human-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

