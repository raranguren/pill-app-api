FROM maven:3.8.3-openjdk-17 as build
COPY pom.xml /usr/src/app/
COPY src /usr/src/app/src/
WORKDIR /usr/src/app
RUN mvn package -Pprod

FROM openjdk:17-jdk-slim as pillapp-api
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
WORKDIR /usr/app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
