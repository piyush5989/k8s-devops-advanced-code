# Source code for backend service to fetch records from DB

This repository is a Java based gradle project which uses a Rest controller to expose below endpoints:
- Get records endpoint [ /records ]
- Simulate Load endpoint [ /fib/{num} ]

This project is containerized using Dockerfile specified at root project location, You can run below commands to build and push the docker image:
```
docker build -t piyush050389/k8s-devops-advanced-code:1.1 .
docker push piyush050389/k8s-devops-advanced-code:1.1
```
