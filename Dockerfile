FROM openjdk:14-alpine
COPY build/libs/micronaut-calc-bot-*-all.jar micronaut-calc-bot.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "micronaut-calc-bot.jar"]