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
    String id1 = cut.newCompass();
    String id2 = cut.newCompass();
    assertThat(id1).isNotEmpty();
    assertThat(id2).isNotEmpty();
    assertThat(id1).isNotEqualTo(id2);
  }

  @Test
  public void idGenerationShouldBeReattemptedWhenGeneratedIdAlreadyExists() {
    final Supplier<String> idGenMock = mock(Supplier.class);
    when(idGenMock.get()).thenReturn("a", "a", "b");
    ValueCompassModel newCut = new ValueCompassModel(idGenMock);
    String id1 = newCut.newCompass();
    assertThat(id1).isEqualTo("a");
    String id2 = newCut.newCompass();
    assertThat(id2).isEqualTo("b");
  }

  @Test
  public void idGenerationShouldThrowAnExceptionWhenItCannotFindAUniqueId() {
    final Supplier<String> idGenMock = mock(Supplier.class);
    when(idGenMock.get()).thenReturn("a");
    ValueCompassModel newCut = new ValueCompassModel(idGenMock);
    newCut.newCompass();
    assertThatThrownBy(newCut::newCompass).isInstanceOf(RuntimeException.class);
  }

  @Test
  public void aNewCompassShouldBeRetrievableById() {
    String id = cut.newCompass();
    assertThat(cut.getCompass(id)).isNotNull();
    assertThat(cut.getCompass(id).getId()).isEqualTo(id);
  }

  @Test
  public void idIsAlphanumeric() {
    String id = cut.newCompass();
    assertThat(id).matches("\\w+");
  }
}