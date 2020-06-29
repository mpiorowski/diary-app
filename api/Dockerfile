# Stage 1 - Create a builder container
FROM openjdk:11 as builder
ENV APP_HOME=/home/test/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
COPY . .

#CMD ["./gradlew", "bootrun"]
#FROM test as builder
RUN ./gradlew build -x test

# Stage 2 - Create a downsized production container
#FROM openjdk:11-jre-slim as prod
FROM openjdk:11 as prod

RUN groupadd --system --gid 1000 prod
RUN useradd --system --gid prod --uid 1000 --shell /bin/bash --create-home prod

USER prod
WORKDIR /home/prod/

COPY --from=builder /home/test/build/libs/api-*.jar /home/prod/app/api.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "/home/prod/app/api.jar"]
