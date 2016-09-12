package io.vertx.example;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import java.util.function.Supplier;

/**
 * Test for {@link ValueCompassModel}
 */
public class ValueCompassModelTest {

  private final ValueCompassModel cut = new ValueCompassModel();

  @Test
  public void creatingANewCompassShouldGenerateAUniqueId() {
    String id1 = cut.newCompass(new CompassCreation("test")).getId();
    String id2 = cut.newCompass(new CompassCreation("test")).getId();
    assertThat(id1).isNotEmpty();
    assertThat(id2).isNotEmpty();
    assertThat(id1).isNotEqualTo(id2);
  }

  @Test
  public void idGenerationShouldBeReattemptedWhenGeneratedIdAlreadyExists() {
    final Supplier<String> idGenMock = mock(Supplier.class);
    when(idGenMock.get()).thenReturn("a", "a", "b");
    ValueCompassModel newCut = new ValueCompassModel(idGenMock);
    String id1 = newCut.newCompass(new CompassCreation("test")).getId();
    assertThat(id1).isEqualTo("a");
    String id2 = newCut.newCompass(new CompassCreation("test")).getId();
    assertThat(id2).isEqualTo("b");
  }

  @Test
  public void idGenerationShouldThrowAnExceptionWhenItCannotFindAUniqueId() {
    final Supplier<String> idGenMock = mock(Supplier.class);
    when(idGenMock.get()).thenReturn("a");
    ValueCompassModel newCut = new ValueCompassModel(idGenMock);
    newCut.newCompass(new CompassCreation("test"));
    assertThatThrownBy(() -> newCut.newCompass(new CompassCreation("test"))).isInstanceOf(RuntimeException.class);
  }

  @Test
  public void aNewCompassShouldBeRetrievableById() {
    String id = cut.newCompass(new CompassCreation("test")).getId();
    assertThat(cut.getCompass(id)).isNotNull();
    assertThat(cut.getCompass(id).getId()).isEqualTo(id);
  }

  @Test
  public void idIsAlphanumeric() {
    String id = cut.newCompass(new CompassCreation("test")).getId();
    assertThat(id).matches("\\w+");
  }

  @Test
  public void theNewCompassHasTheGivenName() {
    String id = cut.newCompass(new CompassCreation("test")).getId();
    assertThat(cut.getCompass(id).getName()).isEqualTo("test");
  }

  @Test
  public void theNewCompassMustHaveAName() {
    assertThatThrownBy(() -> cut.newCompass(new CompassCreation("  "))).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cut.newCompass(new CompassCreation(""))).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cut.newCompass(new CompassCreation(null))).isInstanceOf(IllegalArgumentException.class);
  }
}