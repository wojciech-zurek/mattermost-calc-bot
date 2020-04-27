FROM gradle:6.3.0-jdk8 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

FROM openjdk:14-alpine
COPY --from=builder /home/gradle/src/build/libs/mattermost-calc-bot-*-all.jar mattermost-calc-bot.jar
EXPOSE 8080
ENTRYPOINT ["java", "-server", "-Djava.security.egd=file:/dev/./urandom", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "mattermost-calc-bot.jar"]