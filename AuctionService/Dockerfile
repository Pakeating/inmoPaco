# --- Etapa 1: Build ---

#JVM VERSION:

FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias (esto optimiza el cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y compilamos el .jar
COPY src ./src
RUN mvn clean package -DskipNativeBuild=true -DskipTests -Dproc:full

# --- Etapa 2: Run ---
# Usamos una imagen JRE (Runtime) mucho más pequeña para la ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Creamos un usuario no root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copiamos el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

##GRAALVM VERSION:
#
## Fase 1: Compilación
#FROM ghcr.io/graalvm/native-image-community:21 AS build
#WORKDIR /app
#
## Copiar archivos de Maven
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#
## Copiar código y compilar a nativo
#COPY src ./src
#RUN ./mvnw native:compile -Pnative -DskipTests -DskipITs -DskipNativeTests -Dmaven.test.skip=true
#
## Fase 2: Ejecución
## para desarrollo, incluye ssh, etc
#FROM debian:bookworm-slim
## para entorno productivo
##FROM gcr.io/distroless/cc-debian12
#WORKDIR /app
#
## Instalar librerías necesarias para el binario nativo
#RUN apt-get update && apt-get install -y zlib1g && rm -rf /var/lib/apt/lists/*
#
## Copiar el binario desde la fase de build
## NOTA: Asegúrate de que el nombre coincida con el artifactId de tu pom.xml
#COPY --from=build /app/target/AuctionService ./app-bin
#
#EXPOSE 8081
#ENTRYPOINT ["./app-bin"]