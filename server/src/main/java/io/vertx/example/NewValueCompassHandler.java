package io.vertx.example;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 *
 */
public class NewValueCompassHandler implements Handler<RoutingContext> {
  private final ValueCompassModel valueCompassModel;

  public NewValueCompassHandler(final ValueCompassModel valueCompassModel) {
    this.valueCompassModel = valueCompassModel;
  }

  @Override
  public void handle(final RoutingContext event) {
    final CompassCreation compassCreation = Json.decodeValue(event.getBodyAsString(), CompassCreation.class);
    final HttpServerResponse response = event.response();
    System.out.println("creating new value compass: " + compassCreation);
    try {
      final ValueCompass newCompass = valueCompassModel.newCompass(compassCreation);
      System.out.println("new compass: " + newCompass);
      response
          .setStatusCode(HttpStatus.CREATED)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(newCompass));
    } catch (final IllegalArgumentException ilArgEx) {
      System.err.println("Could nt create value compass: " + ilArgEx.getMessage());
      response
          .setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end("{\"msg\":\"A compass must have a non blank name\"}");
    }
  }
}
