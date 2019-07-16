FROM openjdk:8
ADD target/stackline-api.jar stackline-api.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "stackline-api.jar"]