FROM adoptopenjdk:17-jre
WORKDIR /app
COPY target/*.jar /app/twitter-clone.jar
EXPOSE 8088

CMD ["java", "-jar", "twitter-clone.jar"]