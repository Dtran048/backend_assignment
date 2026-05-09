# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the JAR
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Runtime environment
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR from the first stage
# Note: Adjust 'target/*.jar' if your artifact has a specific name
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port (adjust if using a different port)
EXPOSE 8080

# Run the application using exec form
ENTRYPOINT ["java", "-jar", "app.jar"]