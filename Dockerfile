FROM adoptopenjdk/openjdk11:latest
LABEL ostk.app.name=atm_machine
LABEL ostk.app.type=webservice

ENV TZ=America/Denver
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
