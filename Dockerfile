FROM maven:3.8.7-openjdk-18-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jre-slim

ENV POSTGRES_HOST=localhost
ENV POSTGRES_PORT=5432
ENV POSTGRES_DB=localdb
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin

EXPOSE 8080

COPY --from=build /app/target/miniproject-sawit.jar /app.jar

RUN apt-get update && apt-get install -y postgresql-client

CMD ["java", "-jar", "/app.jar"]
