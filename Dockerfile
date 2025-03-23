# Сборка
FROM --platform=linux/arm64 maven:3.9.6 AS build
WORKDIR /app
COPY . .

# Чтение версии из pom.xml
ARG APP_VERSION
RUN APP_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout) && \
    echo "APP_VERSION=${APP_VERSION}" >> /app/version.env

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Финальный образ
FROM --platform=linux/arm64 eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/version.env /app/version.env

# Добавление метки с версией
LABEL version="${APP_VERSION}"

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]