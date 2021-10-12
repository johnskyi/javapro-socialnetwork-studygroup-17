FROM  adoptopenjdk/openjdk11:alpine
VOLUME /usr/app
EXPOSE 8086
ARG JAR_FILE=target/SocialNetwork-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} SSocialNetwork-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","SocialNetwork-0.0.1-SNAPSHOT.jar"]