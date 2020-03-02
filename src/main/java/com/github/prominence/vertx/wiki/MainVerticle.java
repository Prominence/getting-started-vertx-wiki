package com.github.prominence.vertx.wiki;

import io.reactivex.Single;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.reactivex.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) {
    Single<String> dbVerticleDeployment = vertx.rxDeployVerticle("io.vertx.guides.wiki.database.WikiDatabaseVerticle");

    DeploymentOptions opts = new DeploymentOptions().setInstances(2);
    dbVerticleDeployment
      .flatMap(id -> vertx.rxDeployVerticle("io.vertx.guides.wiki.http.HttpServerVerticle", opts))
      .subscribe(id -> promise.complete(), promise::fail);
  }
}
