package io.vertx.example;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 *
 */
public class GetValueCompassHandler implements Handler<RoutingContext> {
  private final ValueCompassModel valueCompassModel;

  public GetValueCompassHandler(final ValueCompassModel valueCompassModel) {
    this.valueCompassModel = valueCompassModel;
  }

  @Override
  public void handle(final RoutingContext event) {
    String id = event.pathParam("id");
    System.out.println(id);

    final HttpServerResponse response = event.response();
    System.out.println("getting value compass: " + id);
    final ValueCompass compass = valueCompassModel.getCompass(id);
    if (compass == null) {
      System.err.println("compass id unknown: " + id);
      response
          .setStatusCode(HttpStatus.NOT_FOUND)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end("{\"msg\":\"A compass with id " + id + " does not exist\"}");
    } else {
      System.out.println("compass: " + compass);
      response
          .setStatusCode(HttpStatus.OK)
          .putHeader("content-type", "application/json; charset=utf-8")
          .putHeader("Access-Control-Allow-Origin", "*")
          .end(Json.encodePrettily(compass));
    }

  }
}
