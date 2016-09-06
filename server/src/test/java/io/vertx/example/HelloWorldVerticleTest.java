/*
 * This document contains trade secret data which is the property of
 * IAV GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from IAV GmbH.
 *
 * Copyright (C) IAV GmbH / Gifhorn / Germany
 */
package io.vertx.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class HelloWorldVerticleTest {

  private Vertx vertx;

  @Before
  public void setUp(final TestContext context) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(HelloWorldVerticle.class.getName(),
        context.asyncAssertSuccess());
  }

  @After
  public void tearDown(final TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void shouldAnswerHelloWorld(final TestContext context) throws Exception {
    final Async async = context.async();

    vertx.createHttpClient().getNow(8080, "localhost", "/api",
        response -> {
          response.handler(body -> {
            context.assertTrue(body.toString().contains("Hello"));
            async.complete();
          });
        });
  }
}
