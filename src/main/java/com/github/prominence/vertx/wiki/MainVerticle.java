package com.github.prominence.vertx.wiki;

import com.github.prominence.vertx.wiki.database.WikiDatabaseVerticle;
import com.github.prominence.vertx.wiki.http.AuthInitializerVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) {
    Promise<String> dbDeploymentPromise = Promise.promise();
    vertx.deployVerticle(new WikiDatabaseVerticle(), dbDeploymentPromise);

    Future<String> authDeploymentFuture = dbDeploymentPromise.future().compose(id -> {
      Promise<String> deployPromise = Promise.promise();
      vertx.deployVerticle(new AuthInitializerVerticle(), deployPromise);
      return deployPromise.future();
    });

    Future<String> httpDeploymentFuture = authDeploymentFuture.compose(id -> {
      Promise<String> deployPromise = Promise.promise();
      vertx.deployVerticle("com.github.prominence.vertx.wiki.http.HttpServerVerticle", new DeploymentOptions().setInstances(2), deployPromise);
      return deployPromise.future();
    });

    httpDeploymentFuture.setHandler(ar -> {
      if (ar.succeeded()) {
        promise.complete();
      } else {
        promise.fail(ar.cause());
      }
    });
  }
}
