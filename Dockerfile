# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to the non-root user
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring:spring

# Expose the application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Wait for database to be ready before starting the app
ENTRYPOINT ["sh", "-c", "\
  echo 'Waiting for database...'; \
  while ! nc -z ${DB_HOST:-postgres} ${DB_PORT:-5432}; do \
    sleep 1; \
  done; \
  echo 'Database is ready!'; \
  java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -jar app.jar"]

