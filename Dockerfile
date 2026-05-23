FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-Duser.timezone=UTC", "-jar", "target/urlShortener-0.0.1-SNAPSHOT.jar"]
