# Use a base Java image with Maven pre-installed (for building the .war file)
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files (pom.xml) to the working directory
COPY pom.xml ./

# Download the dependencies (skip tests to speed up the build)
RUN mvn dependency:go-offline -B

# Copy the entire source code into the working directory
COPY src ./src

# Build the WAR file using Maven
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk-alpine

# Copy the Spring Boot jar file into the container
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} app.war

# Expose the default Spring Boot port
EXPOSE 8091

# Run the application
ENTRYPOINT ["java", "-jar", "app.war"]
