FROM openjdk:latest
MAINTAINER Team JavaPRO <x.noreply@yzq.org>
ARG JAR_FILE=target/SocialNetwork-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} SocialNetwork-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "SocialNetwork-0.0.1-SNAPSHOT.jar"]





