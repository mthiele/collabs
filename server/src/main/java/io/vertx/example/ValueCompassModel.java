package io.vertx.example;

import org.apache.commons.lang.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 */
public class ValueCompassModel {
  private Map<String, ValueCompass> compassMap = new HashMap<>();

  private Supplier<String> idGenerator = () -> RandomStringUtils.randomAlphanumeric(8);

  public ValueCompassModel() {
  }

  public ValueCompassModel(final Supplier<String> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public String newCompass() {
    String id;
    int attempts = 100;
    do {
      if (attempts-- == 0) {
        throw new RuntimeException("could not find a unique id :-(");
      }
      id = idGenerator.get();
    } while (compassMap.containsKey(id));
    compassMap.put(id, new ValueCompass(id));
    return id;
  }

  public ValueCompass getCompass(final String id) {
    return compassMap.get(id);
  }
}
