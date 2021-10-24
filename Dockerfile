FROM openjdk:latest
MAINTAINER Team JavaPRO <x.noreply@yzq.org>
ARG JAR_FILE=target/social_network-0.1.jar
ADD target/${JAR_FILE} social_network-0.1.jar
ENTRYPOINT ["java", "-jar", "social_network-0.1.jar"]





