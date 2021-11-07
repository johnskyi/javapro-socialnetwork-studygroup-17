FROM  openjdk:11
VOLUME /usr/app
EXPOSE 8086
ARG JAR_FILE=target/social_network-0.1.jar
ADD ${JAR_FILE} social_network-0.1.jar
ENTRYPOINT ["java","-jar","social_network-0.1.jar"]





