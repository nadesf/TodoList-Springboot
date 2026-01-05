# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY src ./src

# Compiler l'application
RUN mvn clean package -DskipTests
# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copier le JAR depuis le stage de build
COPY --from=build /app/target/*.jar app.jar

# Copier l'agent Sentry OpenTelemetry depuis la racine du projet
COPY sentry-opentelemetry-agent-*.jar /app/sentry-opentelemetry-agent.jar

# Exposer le port
EXPOSE 8080
ENV SENTRY_DSN=https://7f0ca0ae0b6c14ee64999d8749ae6039@local-apm.bmiwfs-tech.com/6

# Lancer l'application avec l'agent Sentry OpenTelemetry
ENTRYPOINT ["java", "-javaagent:/app/sentry-opentelemetry-agent.jar", "-jar", "app.jar"]
