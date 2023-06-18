FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
COPY target/*.jar backoffice.jar
ENTRYPOINT ["java","-jar","/backoffice.jar"]