FROM maven:3.9-eclipse-temurin-25

WORKDIR /app


COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline

CMD ["mvn", "spring-boot:run"]