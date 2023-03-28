FROM eclipse-temurin:20-jre

#FROM eclipse-temurin:17-jdk-jammy

COPY counter.jar /tmp/counter.jar

WORKDIR /tmp
EXPOSE 8080

CMD ["java", "-jar", "counter.jar"]