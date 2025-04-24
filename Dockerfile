FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copie os arquivos de projeto
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
COPY system.properties .

# Dê permissão de execução ao Maven wrapper
RUN chmod +x ./mvnw

# Construa o projeto
RUN ./mvnw package -DskipTests

# Imagem de runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copie o jar do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta que a aplicação usa
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]