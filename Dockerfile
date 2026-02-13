# Etapa 1: Compilación (Builder)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos o pom e descargamos dependencias para aproveitar a caché
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos o código e xeramos o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Execución (Runtime)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos o JAR desde a etapa anterior (asume que o nome é app.jar ou similar)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]