FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /app
ENV PROJECT_HOME /app

WORKDIR $PROJECT_HOME

EXPOSE 8080

COPY build/libs/*.jar service.jar

ENTRYPOINT java -Xverify:none -jar service.jar
