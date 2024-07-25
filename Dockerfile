FROM maven:3.9.8 as builder
WORKDIR /opt/app
COPY flex/.mvn .mvn
COPY flex/mvnw flex/pom.xml ./
RUN ./mvnw dependency:go-offline
COPY flex/src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:21
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]