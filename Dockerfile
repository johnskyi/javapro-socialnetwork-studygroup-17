FROM openjdk:latest
MAINTAINER Team JavaPRO <x.noreply@yzq.org>
ARG JAR_FILE=.m2/repository/ru/skillbox/socialnetwork/social_network/0.1/social_network-0.1.jar
ADD ${JAR_FILE} social_network-0.1.jar
ENTRYPOINT ["java","-jar","social_network-0.1.jar"]
