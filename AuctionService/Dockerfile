# --- Etapa 1: Build ---

FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias (esto optimiza el cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y compilamos el .jar
COPY src ./src
RUN mvn clean package -DskipNativeBuild=true -DskipTests

# --- Etapa 2: Run ---
# Usamos una imagen JRE (Runtime) mucho más pequeña para la ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Creamos un usuario no root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copiamos el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de la aplicación
EXPOSE 8081

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]