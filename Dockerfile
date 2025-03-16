# Сборка
FROM maven:3.8.7-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Оптимизация для Raspberry Pi
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]