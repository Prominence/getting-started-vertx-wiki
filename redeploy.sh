#!/usr/bin/env bash

export LAUNCHER="io.vertx.core.Launcher"
export VERTICLE="com.github.prominence.vertx.wiki.MainVerticle"
export CMD="mvn compile"
export VERTX_CMD="run"

mvn compile dependency:copy-dependencies
java \
  -cp  $(echo target/dependency/*.jar | tr ' ' ':'):"target/classes" \
  $LAUNCHER $VERTX_CMD $VERTICLE \
  --redeploy="src/main/**/*" --on-redeploy="$CMD" \
  --launcher-class=$LAUNCHER \
  $@
