# Build-Stage
FROM maven:3.9-amazoncorretto-11-al2023 AS build
WORKDIR /app

# Kopiere nur die pom.xml Dateien und führe dependency:go-offline aus
COPY pom.xml /app/pom.xml
COPY 0-cleanproject-plugins/pom.xml /app/0-cleanproject-plugins/pom.xml
COPY 1-cleanproject-adapters/pom.xml /app/1-cleanproject-adapters/pom.xml
COPY 2-cleanproject-application/pom.xml /app/2-cleanproject-application/pom.xml
COPY 3-cleanproject-domain/pom.xml /app/3-cleanproject-domain/pom.xml
COPY 4-cleanproject-abstractioncode/pom.xml /app/4-cleanproject-abstractioncode/pom.xml

# Installiere die Abhängigkeiten, um Caching zu nutzen
RUN mvn dependency:go-offline

# Kopiere den restlichen Quellcode und baue das Projekt
COPY 0-cleanproject-plugins /app/0-cleanproject-plugins
COPY 1-cleanproject-adapters /app/1-cleanproject-adapters
COPY 2-cleanproject-application /app/2-cleanproject-application
COPY 3-cleanproject-domain /app/3-cleanproject-domain
COPY 4-cleanproject-abstractioncode /app/4-cleanproject-abstractioncode

RUN mvn clean package -DskipTests

# Run-Stage
FROM amazoncorretto:11-alpine
WORKDIR /app
COPY --from=build /app/0-cleanproject-plugins/target/*.jar /app/backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
