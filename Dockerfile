FROM openjdk:15.0.2-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY ./build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]
