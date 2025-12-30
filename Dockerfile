# Build stage
FROM maven:3.9.12-eclipse-temurin-25 AS build
WORKDIR /gestion-stock

COPY . .
RUN mvn package -DskipTests -P prod

FROM openjdk:25-ea
ARG VERSION
COPY --from=build /gestion-stock/gestion-stock-application/target/gestion-stock-application-${VERSION}.jar /app/gestion-stock-application.jar
ENTRYPOINT ["java","-jar","/app/gestion-stock-application.jar"]