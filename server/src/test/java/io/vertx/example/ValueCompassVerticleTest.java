package io.vertx.example;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class ValueCompassVerticleTest {

  private final ValueCompassModel valueCompassModel = spy(new ValueCompassModel());
  private final ValueCompassVerticle cut = new ValueCompassVerticle(valueCompassModel);

  private Vertx vertx;

  @Before
  public void setUp(final TestContext context) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(cut, context.asyncAssertSuccess());
    vertx.exceptionHandler(context.exceptionHandler());
  }

  @After
  public void tearDown(final TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void shouldAnswerHelloWorld(final TestContext context) throws Exception {
    final Async async = context.async();

    vertx.createHttpClient().getNow(8080, "localhost", "/api/hello",
        response -> {
          response.handler(body -> {
            context.assertTrue(body.toString().contains("Hello"));
            async.complete();
          });
        });
  }

  @Test
  public void anExistingValueCompassCanBeRetrievedById(final TestContext context) throws Exception {
    final Async async = context.async();

    String testId = "testId";
    String testName = "test compass name";
    when(valueCompassModel.getCompass(testId)).thenReturn(new ValueCompass(testId, testName));

    vertx.createHttpClient().getNow(8080, "localhost", "/api/valueCompass/" + testId,
        response -> {
          response.handler(body -> {
            final ValueCompass valueCompass = Json.decodeValue(body.toString(), ValueCompass.class);
            context.assertEquals(valueCompass.getId(), testId);
            context.assertEquals(valueCompass.getName(), testName);
            async.complete();
          });
          response.exceptionHandler(context.exceptionHandler());
        });
  }

  @Test
  public void aNewCompassCanBeCreated(final TestContext context) throws Exception {
    Async async = context.async();
    final String json = "{\"name\":\"test\"}";
    final String length = Integer.toString(json.length());
    vertx.createHttpClient().post(8080, "localhost", "/api/valueCompass")
        .putHeader("content-type", "application/json")
        .putHeader("content-length", length)
        .handler(response -> {
          context.assertEquals(response.statusCode(), 201);
          context.assertTrue(response.headers().get("content-type").contains("application/json"));
          response.bodyHandler(body -> {
            final ValueCompass valueCompass = Json.decodeValue(body.toString(), ValueCompass.class);
            context.assertEquals(valueCompass.getName(), "test");
            context.assertNotNull(valueCompass.getId());
            async.complete();
          });
          response.exceptionHandler(context.exceptionHandler());
        })
        .write(json)
        .end();
  }
}

