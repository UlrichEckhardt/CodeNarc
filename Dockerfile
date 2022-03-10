#### Build Image
FROM gradle:7.6-jdk11-alpine AS gradle

WORKDIR /gradle
COPY . .
RUN gradle --quiet --no-daemon shadowJar

# built JARs are tagged with their version number
# Note: This could be retrieved from ./build/resources/main/codenarc-version.txt as well.
RUN cp /gradle/build/libs/CodeNarc-*-all.jar /gradle/build/libs/codenarc-all.jar

#### Runtime Image
FROM openjdk:11-jre-slim

COPY --from=gradle /gradle/build/libs/codenarc-all.jar /lib/codenarc-all.jar

WORKDIR /ws

ENTRYPOINT ["java", "-jar", "/lib/codenarc-all.jar"]
