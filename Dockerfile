FROM openjdk:17-oracle

VOLUME /tmp
COPY build/libs/*.jar app.jar

# Add environment variables
ENV REDIS_HOST=localhost
ENV REDIS_PORT=6379
ENV SQL_DATABASE_URL=jdbc:postgresql://localhost:54322/postgres
ENV SQL_PASSWORD=postgres
ENV SQL_USERNAME=postgres

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

