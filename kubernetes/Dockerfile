FROM maven:3.9-eclipse-temurin-25
WORKDIR /app
COPY target/kubernetes-1.0.0.jar /app/kubernetes-1.0.0.jar
EXPOSE 8080

CMD ["java" ,"-jar",  "kubernetes-1.0.0.jar"]