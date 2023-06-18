FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
COPY target/*.jar order-processing.jar
ENTRYPOINT ["java","-jar","/order-processing.jar"]