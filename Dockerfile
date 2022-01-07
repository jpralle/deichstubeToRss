FROM markhobson/maven-chrome:jdk-16

#run as non-priviliged user
RUN groupadd --system spring && useradd --system -g spring spring
USER spring:spring

EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar", "--chrome.binary.path=/usr/bin/chromedriver"]
