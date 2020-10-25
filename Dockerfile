FROM java:8-jdk-alpine
COPY ./build/libs/GeekBot.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "GeekBot.jar"]