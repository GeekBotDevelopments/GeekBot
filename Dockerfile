FROM openjdk:15-alpine3.12

EXPOSE 8080

RUN mkdir /app

COPY ./build/libs/*.jar /app/spring-boot-application.jar

CMD ["java", "-Djava.security.egd=file:/dev/urandom", "-jar" ,"/app/spring-boot-application.jar"]
