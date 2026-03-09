# Builder stage: compile the application using Maven
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy pom first to cache dependencies
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B -DskipTests package

# Runtime stage: smaller JRE image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the fat jar from the builder stage
COPY --from=builder /workspace/target/*.jar app.jar

# Expose the port configured by the application (default 8080)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

