FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
COPY --from=build /target/as2-0.0.1-SNAPSHOT.jar grow-with-you.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","grow-with-you.jar"]