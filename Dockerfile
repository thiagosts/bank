FROM amazoncorretto:17-alpine3.16

LABEL authors="Thiago Santos"

VOLUME /tmp

EXPOSE 8081:8081

COPY target/service-0.0.1-SNAPSHOT.jar service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/service-0.0.1-SNAPSHOT.jar"]