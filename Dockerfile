FROM maven:3.6.3-openjdk-11-slim AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/bankproject-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "bankproject-0.0.1-SNAPSHOT.jar"]