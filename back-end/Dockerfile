FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Sadece pom.xml'i kopyala ve bağımlılıkları indir.
# Neden: Kod değişse bile kütüphaneler değişmediyse bu adımı önbellekten (cache) hızlıca geçsin diye.
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# target klasöründeki dosya ismini pom.xml'den biliyoruz.
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]