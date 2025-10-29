# Use OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy all files (including pom.xml & src)
COPY . .

# Install Maven (for building inside container)
RUN apt-get update && apt-get install -y maven

# Build the jar file
RUN mvn clean package -DskipTests

# Copy the jar to app.jar
RUN cp target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
