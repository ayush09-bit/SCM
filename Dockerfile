
# ---- Stage 1: Build the Spring Boot application using JDK 21 ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom & source
COPY pom.xml .
COPY src ./src

# Build the project (skip tests for faster build)
RUN mvn clean package -DskipTests


# ---- Stage 2: Run the app using a lightweight JRE 21 image ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Corrected COPY path (no double target folder)
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
