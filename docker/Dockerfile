# https://github.com/markhobson/docker-maven-chrome/blob/master/jdk-16/Dockerfile
FROM markhobson/maven-chrome:latest

# Add and run as non-privileged
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Add JAR
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]