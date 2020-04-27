#!/usr/bin/env sh

#./gradlew clean shadowJar
docker build -t wojciechzurek/mattermost-calc-bot:0.1 -f Dockerfile .