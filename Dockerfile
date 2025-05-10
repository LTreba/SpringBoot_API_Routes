FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY system.properties .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
VOLUME /data

# Copy the jar with explicit name
COPY --from=build /app/target/racetracker-1.0-SNAPSHOT.jar /app/app.jar

# Create directory for the database with proper permissions
RUN mkdir -p /data && chmod 777 /data

ENTRYPOINT ["java", "-jar", "/app/app.jar"]