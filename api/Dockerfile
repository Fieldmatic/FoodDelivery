FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
COPY target/*.jar api.jar
ENTRYPOINT ["java","-jar","/api.jar"]