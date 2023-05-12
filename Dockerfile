FROM maven AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package install -DskipTests

FROM openjdk:17-alpine AS deploy
WORKDIR /home/app/src
EXPOSE 8080
COPY --from=build /home/app/target/gtbr-account-share-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "gtbr-account-share-0.0.1-SNAPSHOT.jar"]
