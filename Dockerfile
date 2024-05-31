# Step 1: Build the project
FROM gradle:7.6.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle clean build --no-daemon

# Step 2: Create the runtime image
FROM openjdk:11-jre-slim
EXPOSE 8080
COPY --from=build /home/gradle/project/build/libs/*.jar /app/records-api.jar
ENTRYPOINT ["java", "-jar", "/app/records-api.jar"]