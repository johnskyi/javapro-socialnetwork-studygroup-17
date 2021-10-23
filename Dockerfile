FROM openjdk:latest
MAINTAINER Team JavaPRO <x.noreply@yzq.org>
ARG JAR_FILE=/javapro/social_network-0.1.jar
ADD ${JAR_FILE} social_network-0.1.jar
ENTRYPOINT ["java", "-jar", "/javapro/social_network-0.1.jar"]
