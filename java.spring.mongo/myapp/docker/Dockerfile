FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/myapp
ADD ./myapp.jar /usr/local/myapp/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh