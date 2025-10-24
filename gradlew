#!/bin/sh

APP_HOME=$( cd "${0%/*}" && pwd  )
GRADLE_WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

exec java -jar "$GRADLE_WRAPPER_JAR" "$@"
