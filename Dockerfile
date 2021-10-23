FROM openjdk:latest
MAINTAINER Team JavaPRO <x.noreply@yzq.org>
ARG JAR_FILE
ADD target/${JAR_FILE} /org/javapro/social_network-0.1.jar
ENTRYPOINT ["java", "-jar", "/javapro/social_network-0.1.jar"]


