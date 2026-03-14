# Usamos una imagen oficial de Java ligera como base
FROM eclipse-temurin:21-jre-alpine

# Creamos una carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo .jar que Maven compila a la carpeta /app del contenedor
COPY target/*.jar app.jar

# Le decimos al contenedor qué comando ejecutar al encenderse
ENTRYPOINT ["java", "-jar", "app.jar"]
