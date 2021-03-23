FROM openjdk:latest
COPY ./target/SemGroupProject.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "SemGroupProject.jar", "database:3306"]