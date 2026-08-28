FROM maven:3.9-eclipse-temurin-25

WORKDIR /app
#EXPOSE 8080
#COPY ./target/product-service-0.0.1-SNAPSHOT.jar .
#CMD ["java", "-jar" , "product-service-0.0.1-SNAPSHOT.jar"]


COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline

CMD ["mvn", "spring-boot:run"]



