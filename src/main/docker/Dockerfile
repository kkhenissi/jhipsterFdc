FROM java:11-jre-alpine
VOLUME /tmp
# ADD /target/backend-app-0.0.1-SNAPSHOT.jar app.jar
ADD /target/jhipster-fdc-0.0.1-SNAPSHOT.jar app.jar

RUN sh -c 'touch /app.jar'
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]

EXPOSE 8888
