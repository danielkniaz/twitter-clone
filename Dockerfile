FROM adoptopenjdk:17-jre
WORKDIR /app
COPY target/*.jar /app/social-network.jar
EXPOSE 8088

CMD ["java", "-jar", "social-network.jar"]