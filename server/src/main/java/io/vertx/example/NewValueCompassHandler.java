package io.vertx.example;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 *
 */
public class NewValueCompassHandler implements Handler<RoutingContext> {
  private ValueCompassModel valueCompassModel;

  public NewValueCompassHandler(final ValueCompassModel valueCompassModel) {
    this.valueCompassModel = valueCompassModel;
  }

  @Override
  public void handle(final RoutingContext event) {
    HttpServerResponse response = event.response();
    System.out.println("creating new value compass...");
    String newCompassId = valueCompassModel.newCompass();
    System.out.println("new compass id: " + newCompassId);
    response
        .putHeader("content-type", "text/plain")
        .end(newCompassId);
  }
}
