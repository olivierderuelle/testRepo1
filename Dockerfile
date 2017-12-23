FROM openjdk:8-jdk-alpine

# Adding a /tmp volume. Docker will map this to /var/lib/docker on the host system. 
# This is the directory Spring Boot will configure Tomcat to use as its working directory.
VOLUME /tmp
#ARG WAR_FILE
#ADD ${WAR_FILE} app.war
ADD target/test1.war app.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]