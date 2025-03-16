# Smart Greenhouse Backend

Это backend-приложение для системы "Умная теплица". Оно управляет данными с датчиков, взаимодействует с базой данных PostgreSQL и MQTT брокером (Mosquitto).

---

## Содержание
1. [Описание сервиса](#описание-сервиса)
2. [Переменные окружения](#переменные-окружения)
3. [CI/CD Pipeline](#ci-cd-pipeline)
4. [Локальная разработка](#локальная-разработка)

---

## Описание сервиса

Сервис предоставляет REST API для управления данными теплицы:
- **Датчики**: Температура, влажность, освещенность.
- **Устройства**: Управление светом, поливом, вентиляцией.
- **База данных**: Хранение истории показаний.
- **MQTT**: Взаимодействие с IoT устройствами.

---

## Переменные окружения

### Основные переменные (application-prod.yml)

| Переменная               | Описание                     | Значение по умолчанию                              |
|--------------------------|------------------------------|----------------------------------------------------|
| `DB_HOST`                | Хост PostgreSQL              | `polytech-smart-greenhouse-postgres`               |
| `DB_PORT`                | Порт PostgreSQL              | `5432`                                             |
| `DB_NAME`                | Имя базы данных              | `polytech-smart-house`                             |
| `DB_USER`                | Пользователь PostgreSQL      | `user`                                             |
| `DB_PASSWORD`            | Пароль PostgreSQL            | `password`                                         |
| `MQTT_HOST`              | Хост MQTT брокера            | `polytech-smart-greenhouse-mosquitto`              |
| `MQTT_PORT`              | Порт MQTT брокера            | `1883`                                             |
| `SPRING_PROFILES_ACTIVE` | Активный профиль Spring Boot | `prod`                                             |
| `JAVA_OPTS`              | Параметры JVM                | `-XX:+UseContainerSupport -XX:MaxRAMPercentage=75` |

---

## CI CD Pipeline

GitHub Actions автоматизирует сборку, тестирование и деплой приложения.

### Этапы CI/CD:
1. **Сборка и тестирование**:
    - Устанавливает JDK 21.
    - Запускает Docker Compose для поднятия зависимостей (Postgres, Mosquitto).
    - Выполняет сборку и тесты с помощью Maven.

2. **Деплой**:
    - Собирает Docker образ для ARM (Raspberry Pi).
    - Публикует образ в Docker Hub.

### Переменные CI/CD:
| Переменная           | Описание                  |
|----------------------|---------------------------|
| `DOCKERHUB_IMAGE`    | Имя образа в Docker Hub   |
| `DOCKERHUB_USERNAME` | Логин Docker Hub (секрет) |
| `DOCKERHUB_PASSWORD` | Токен Docker Hub (секрет) |

---

## Локальная разработка

### Запуск приложения
```bash
./mvnw spring-boot:run