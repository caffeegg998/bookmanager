FROM eclipse-temurin:17-jre-alpine

COPY target/usermanager-0.0.1-SNAPSHOT.jar /home/usermanager-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/home/usermanager-0.0.1-SNAPSHOT.jar"]