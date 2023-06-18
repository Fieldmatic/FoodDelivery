FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
COPY target/*.jar order-search.jar
ENTRYPOINT ["java","-jar","/order-search.jar"]