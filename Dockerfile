FROM adoptopenjdk/openjdk11:latest
LABEL ostk.app.name=atm_machine
LABEL ostk.app.type=webservice

ENV TZ=America/Denver
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
