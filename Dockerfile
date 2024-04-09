    syntax=docker/dockerfile:1

    FROM openjdk:23-windowsservercore-ltsc2022

    #working directory
    WORKDIR /app

    #copy from your Host to container
    COPY .mvn/ .mvn
    COPY mvnw pom.xml ./


    #Run this inside the image
    RUN ./mvnw  dependency:go-offline
    COPY src ./src

    #run inside container
    CMD ["./mvnw", "spring-boot:run"]

