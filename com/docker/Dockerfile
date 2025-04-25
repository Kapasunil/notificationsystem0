FROM openjdk:17-jdk-slim

WORKDIR /app

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

COPY build/libs/com-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["./wait-for-it.sh", "postgres-db:5432", "--", "java", "-jar", "app.jar"]
