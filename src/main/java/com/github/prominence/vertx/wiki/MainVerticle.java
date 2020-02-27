package com.github.prominence.vertx.wiki;

import com.github.prominence.vertx.wiki.http.AuthInitializerVerticle;
import io.reactivex.Single;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.reactivex.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) {
    Single<String> dbVerticleDeployment = vertx.rxDeployVerticle("com.github.prominence.vertx.wiki.http.HttpServerVerticle");
    dbVerticleDeployment
      .flatMap(id -> vertx.rxDeployVerticle(
        "com.github.prominence.vertx.wiki.database.WikiDatabaseVerticle", new DeploymentOptions().setInstances(2)
      ))
      .flatMap(id -> vertx.rxDeployVerticle(new AuthInitializerVerticle()))
      .subscribe(id -> promise.complete(), promise::fail);
  }
}
