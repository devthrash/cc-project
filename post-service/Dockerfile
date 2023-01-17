FROM adoptopenjdk/openjdk11:alpine-jre

COPY ./target/post-service-0.0.1-SNAPSHOT.jar /app-service/post-service.jar
WORKDIR /app-service/.
EXPOSE 8080 8081
ENTRYPOINT ["java","-jar","post-service.jar"]
