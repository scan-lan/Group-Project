FROM openjdk:latest
COPY ./target/SemGroupProject.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "SemGroupProject.jar", "db:3306"]