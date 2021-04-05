FROM openjdk:11

ARG JAR_FILE
COPY ${JAR_FILE} /provision.jar

ENTRYPOINT ["java", "-jar", "/provision.jar"]