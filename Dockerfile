# Build stage
FROM maven:3.9.1-eclipse-temurin-11 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:17-jdk
COPY --from=build /target/codecompiler-0.0.1-SNAPSHOT.jar codecompiler.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","codecompiler.jar"]