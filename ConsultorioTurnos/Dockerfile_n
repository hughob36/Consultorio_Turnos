# Usamos imagen oficial de OpenJDK 21
FROM openjdk:21-jdk-slim

# Directorio de trabajo dentro del contenedor
ARG JAR_FILE=target/ConsultorioTurnos-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app_gestionTurno.jar
# Exponemos el puerto que usa Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","app_gestionTurno.jar"]


