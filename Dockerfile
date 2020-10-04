FROM openjdk:11.0.8
VOLUME /tmp
ADD ./target/tran-0.0.1-SNAPSHOT.jar tran.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/tran.jar"]