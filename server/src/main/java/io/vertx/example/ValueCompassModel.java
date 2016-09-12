package io.vertx.example;

import com.google.common.base.Strings;

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

  public ValueCompass newCompass(final CompassCreation compassCreation) {
    checkPreconditions(compassCreation);
    String id = generateId();
    ValueCompass valueCompass = new ValueCompass(id, compassCreation.getName());
    compassMap.put(id, valueCompass);
    return valueCompass;
  }

  private String generateId() {
    String id;
    int attempts = 100;
    do {
      if (attempts-- == 0) {
        throw new RuntimeException("could not find a unique id :-(");
      }
      id = idGenerator.get();
    } while (compassMap.containsKey(id));
    return id;
  }

  private void checkPreconditions(final CompassCreation compassCreation) {
    if (Strings.isNullOrEmpty(compassCreation.getName()) || compassCreation.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("new compass must have a name");
    }
  }

  public ValueCompass getCompass(final String id) {
    return compassMap.get(id);
  }
}
