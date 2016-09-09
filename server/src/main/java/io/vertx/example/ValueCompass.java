package io.vertx.example;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Represents a value compass with its votings.
 */
public class ValueCompass {
  private String id;

  public ValueCompass(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
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
        .toString();
  }
}
