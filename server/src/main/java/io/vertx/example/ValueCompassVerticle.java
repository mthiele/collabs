package io.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ValueCompassVerticle extends AbstractVerticle {

  private ValueCompassModel valueCompassModel;
  private NewValueCompassHandler newValueCompassHandler;

  public ValueCompassVerticle() {
    valueCompassModel = new ValueCompassModel();
    newValueCompassHandler = new NewValueCompassHandler(valueCompassModel);
  }

  @Override
  public void start() {
    final Router router = Router.router(vertx);
    router.route("/eventbus/*").handler(eventBusHandler());
    router.route("/api/hello").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response
          .putHeader("content-type", "text/html")
          .end("<h1>Hello from my first Vert.x 3 application</h1>");
    });
    router.route("/api/valueCompass").handler(BodyHandler.create());
    router.post("/api/valueCompass").handler(newValueCompassHandler::handle);

    vertx.createHttpServer().
        requestHandler(router::accept).
        listen(8080);

    vertx.setPeriodic(1000, event -> vertx.eventBus().
        publish("yeah", "omg!"));
  }

  private SockJSHandler eventBusHandler() {
    BridgeOptions options = new BridgeOptions()
        .addOutboundPermitted(new PermittedOptions().setAddressRegex(".*"));
    return SockJSHandler.create(vertx).bridge(options, event -> {
      if (event.type() == BridgeEventType.SOCKET_CREATED) {
        System.out.println("A socket was created");
      }
      event.complete(true);
    });
  }
}
