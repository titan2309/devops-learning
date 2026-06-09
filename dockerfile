# Build Stage
FROM maven:3.9.11-eclipse-temurin-25 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

# Runtime Stage
FROM eclipse-temurin:25-jre

RUN useradd -r spring

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]