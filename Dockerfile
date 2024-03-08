FROM openjdk:11
MAINTAINER aeltayary@dxc.com

VOLUME /tmp


# Define environment variable
ENV JAVA_OPTIONS=""

ARG JAR_FILE
COPY  ${JAR_FILE} /opt/app.jar

WORKDIR /opt
#EXPOSE 8080

# Run accounts-microservice.jar when the container launches
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/opt/app.jar"]