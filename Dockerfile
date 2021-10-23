FROM openjdk:latest
MAINTAINER Team JavaPRO <x.noreply@yzq.org>
ENTRYPOINT ["java", "-jar", "/javapro/social_network-0.1.jar"]
ARG JAR_FILE
ADD ${JAR_FILE} /org/javapro/social_network-0.1.jar

