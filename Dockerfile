FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/sixth-sense-0.0.1-SNAPSHOT.jar DeIdentification.jar
ENTRYPOINT ["java", "-jar", "DeIdentification.jar"]