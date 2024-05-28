FROM eclipse-temurin:21

RUN apt-get update && apt-get install -y maven && apt-get clean
# Copy source folder
COPY src src

# Copy maven pom.xml
COPY pom.xml .

# Build the package
RUN mvn clean package

# PORT EXPOSED
EXPOSE 12345

# Run the code
ENTRYPOINT ["java","-jar","target/LB08-1.0-SNAPSHOT-jar-with-dependencies.jar"]