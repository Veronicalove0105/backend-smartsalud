# Etapa de construcción
FROM gradle:8.7-jdk17 AS build
WORKDIR /app
# Copiar el código fuente
COPY . .
# Construir el proyecto omitiendo los tests para acelerar el despliegue
RUN gradle build -x test --no-daemon

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiar el archivo .jar generado en la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
