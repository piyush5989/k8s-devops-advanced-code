# Backend service to fetch records from MySQL database

## Prerequisites
- Java 11
- Gradle
- Docker

### Building the Project

To build the project, run the following command:

```bash
gradle clean build
```

To run the project using Docker, use the following command:

```
docker run -p 8080:8080 piyush050389/k8s-devops-advanced-code:1.1
```

### API Endpoints

- Get /records: Retrieve all records.
- GET /fib/{num}: Simulate load by calculating the Fibonacci number for the given num.
