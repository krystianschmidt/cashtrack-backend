# Build-Stage
FROM maven:3.9-amazoncorretto-11-al2023 AS build
WORKDIR /app

# Kopiere nur die pom.xml Dateien und führe dependency:go-offline aus
COPY pom.xml /app/pom.xml
COPY plugins/pom.xml /app/plugins/pom.xml
COPY adapters/pom.xml /app/adapters/pom.xml
COPY application/pom.xml /app/application/pom.xml
COPY domain/pom.xml /app/domain/pom.xml
COPY abstractioncode/pom.xml /app/abstractioncode/pom.xml

# Installiere die Abhängigkeiten, um Caching zu nutzen
RUN mvn dependency:go-offline

# Kopiere den restlichen Quellcode und baue das Projekt
COPY plugins /app/plugins
COPY adapters /app/adapters
COPY application /app/application
COPY domain /app/domain
COPY abstractioncode /app/abstractioncode

RUN mvn clean package -DskipTests

# Run-Stage
FROM amazoncorretto:11-alpine
WORKDIR /app
COPY --from=build /app/plugins/target/*.jar /app/backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
