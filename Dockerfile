FROM openjdk:latest
MAINTAINER Team JavaPRO <x.noreply@yzq.org>
ENTRYPOINT ["java", "-jar", "/javapro/backend.jar"]
ARG JAR_FILE
ADD target/${JAR_FILE} /org/javapro/backend.jar



