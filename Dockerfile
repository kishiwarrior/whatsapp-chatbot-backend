# Build stage
FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /workspace/target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV PORT=8080
CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --server.port=${PORT:-8080} --server.address=0.0.0.0"]
