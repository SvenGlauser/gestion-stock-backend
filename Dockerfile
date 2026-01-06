# Build stage
FROM maven:3.9.12-eclipse-temurin-25 AS build
WORKDIR /gestion-stock

COPY . .

RUN mvn package -DskipTests -P prod

# Run stage
FROM eclipse-temurin:25
WORKDIR /app
ARG VERSION

COPY --from=build /gestion-stock/gestion-stock-application/target/gestion-stock-application-${VERSION}.jar /gestion-stock-application.jar

ENTRYPOINT ["java","-jar","/gestion-stock-application.jar"]