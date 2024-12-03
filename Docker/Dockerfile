FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY app.jar /app/application.jar

EXPOSE 28500

ENTRYPOINT ["java", "-jar", "application.jar"]

CMD ["--default-arg=value"]
