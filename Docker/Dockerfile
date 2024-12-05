FROM eclipse-temurin:21

WORKDIR /app

COPY app.jar /app/application.jar

EXPOSE 15100

ENTRYPOINT ["java", "-jar", "application.jar"]

CMD ["server"]
