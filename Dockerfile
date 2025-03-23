# Сборка
FROM --platform=linux/arm64 maven:3.9.6 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Финальный образ
FROM --platform=linux/arm64 eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Оптимизация для Raspberry Pi
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]