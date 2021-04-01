FROM gradle:6.8.3-jdk15

RUN git clone https://github.com/LegendaryGeek/GeekBot.git \
 && cd GeekBot \
 && gradle build \
 && mkdir /app \
 && cp ./build/libs/*.jar /app/spring-boot-application.jar

WORKDIR /app

CMD ["java", "-Djava.security.egd=file:/dev/urandom","-jar","/app/spring-boot-application.jar"]
