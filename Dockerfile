FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copie os arquivos de projeto
COPY pom.xml .
COPY src src
COPY system.properties .

# Construa o projeto usando Maven instalado na imagem
RUN mvn package -DskipTests

# Imagem de runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copie o jar do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta que a aplicação usa
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]