FROM maven:3.8.5-openjdk-11 AS maven_build
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
CMD java -jar /data/post-service-0.0.1-SNAPSHOT.jar
COPY --from=maven_build /tmp/target/post-service-0.0.1-SNAPSHOT.jar /data/post-service-0.0.1-SNAPSHOT.jar
