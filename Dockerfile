FROM maven:latest AS build

WORKDIR /server

COPY src /server/src

COPY pom.xml /server/

RUN mvn -f /server/pom.xml clean package

CMD ["java","-jar","/server/target/sql-jdbc-school-1.0-SNAPSHOT-jar-with-dependencies.jar"]