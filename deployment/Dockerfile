FROM eclipse-temurin:21

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t <yourDockerhubUsername>/oms-ms-inventory:latest -f Dockerfile .
# docker push <yourDockerhubUsername>/oms-ms-inventory:latest
