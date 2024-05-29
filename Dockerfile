FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./fetch-record-from-db-api-1.0.jar records-api.jar

ENTRYPOINT ["java", "-jar", "records-api.jar"]