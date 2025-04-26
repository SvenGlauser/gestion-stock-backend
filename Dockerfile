FROM openjdk:21
ARG VERSION
COPY /gestion-stock-application/target/gestion-stock-application-${VERSION}.jar /app/gestion-stock-application.jar
ENTRYPOINT ["java","-jar","/app/gestion-stock-application.jar"]