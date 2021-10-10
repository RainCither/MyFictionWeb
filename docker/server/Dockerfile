FROM java:8
COPY ./server/fiction-0.0.1-SNAPSHOT.jar /usr/local
COPY ./server/application.yaml /usr/local
ENTRYPOINT ["java","-jar","/usr/local/fiction-0.0.1-SNAPSHOT.jar"]