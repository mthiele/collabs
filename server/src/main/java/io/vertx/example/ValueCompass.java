package io.vertx.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Represents a value compass with its votings.
 */
public class ValueCompass {
  private final String id;
  private final String name;

  public ValueCompass(
      @JsonProperty("id") final String id,
      @JsonProperty("name") final String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ValueCompass that = (ValueCompass)o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .toString();
  }
}
