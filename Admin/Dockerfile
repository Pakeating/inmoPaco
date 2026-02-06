# Fase 1: Compilación
FROM ghcr.io/graalvm/native-image-community:21 AS build
WORKDIR /app

# Copiar archivos de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copiar código y compilar a nativo
COPY src ./src
RUN ./mvnw native:compile -Pnative -DskipTests -DskipITs -DskipNativeTests -Dmaven.test.skip=true

# Fase 2: Ejecución
# para desarrollo, incluye ssh, etc
FROM debian:bookworm-slim
# para entorno productivo
#FROM gcr.io/distroless/cc-debian12
WORKDIR /app

# Instalar librerías necesarias para el binario nativo
RUN apt-get update && apt-get install -y zlib1g && rm -rf /var/lib/apt/lists/*

# Copiar el binario desde la fase de build
# NOTA: Asegúrate de que el nombre coincida con el artifactId de tu pom.xml
COPY --from=build /app/target/Admin ./app-bin

EXPOSE 9090
ENTRYPOINT ["./app-bin"]

##SIN GRAAL
## Etapa de construcción
#FROM maven:3.9.6-eclipse-temurin-21 AS build
#COPY . /app
#WORKDIR /app
#RUN mvn clean package -DskipTests
#
## Etapa de ejecución
#FROM eclipse-temurin:21-jre
#WORKDIR /app
## El nombre del jar debe coincidir con el de tu pom.xml (Admin-0.0.1-SNAPSHOT.jar)
#COPY --from=build /app/target/*.jar app.jar
#
#EXPOSE 9090
#ENTRYPOINT ["java", "-jar", "app.jar"]