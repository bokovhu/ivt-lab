package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryTorpedoStore, 
    secondaryTorpedoStore;

  @BeforeEach
  public void init(){
    this.primaryTorpedoStore = mock(TorpedoStore.class);
    this.secondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(
      this.primaryTorpedoStore,
      this.secondaryTorpedoStore
    );
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    // A single torpedo should have been fired from the primary store
    verify(primaryTorpedoStore).fire(1);
    // No torpedos should have been fired from the secondary store
    verify(secondaryTorpedoStore, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(primaryTorpedoStore.fire(anyInt())).thenReturn(true);

    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.getTorpedoCount()).thenReturn(20);
    when(secondaryTorpedoStore.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    // Reported 10 remaining torpedos, all of them should have been fired
    verify(primaryTorpedoStore).fire(10);
    // All 20 reported torpedos should have been fired from the secondary
    verify(secondaryTorpedoStore).fire(20);
  }

  @Test
  public void fireTorpedo_Single_ShouldUseThePrimaryStoreInItsInitialState () {
    // Arrange
    // As-per the test plan, this test case requires a newly constructed instance
    this.ship = new GT4500(
      this.primaryTorpedoStore,
      this.secondaryTorpedoStore
    );
    when (primaryTorpedoStore.isEmpty()).thenReturn(false);
    when (primaryTorpedoStore.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore).fire(1);
    verify(secondaryTorpedoStore, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_ShouldAlternateBetweenStores () {
    // Arrange
    // As-per the test plan, this test case requires a newly constructed instance
    this.ship = new GT4500(
      this.primaryTorpedoStore,
      this.secondaryTorpedoStore
    );
    when (primaryTorpedoStore.isEmpty()).thenReturn(false);
    when (primaryTorpedoStore.fire(anyInt())).thenReturn(true);
    when (secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when (secondaryTorpedoStore.fire(anyInt())).thenReturn(true);

    // Act
    // Fire 3 times
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result &= ship.fireTorpedo(FiringMode.SINGLE);
    result &= ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(2)).fire(1);
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_ShouldReturnFalseWhenStoresAreEmpty () {
    // Arrange
    // As-per the test plan, this test case requires a newly constructed instance
    this.ship = new GT4500(
      this.primaryTorpedoStore,
      this.secondaryTorpedoStore
    );
    when (primaryTorpedoStore.isEmpty()).thenReturn(true);
    when (secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    // Fire 3 times
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryTorpedoStore, never()).fire(anyInt());
    verify(secondaryTorpedoStore, never()).fire(anyInt());
    verify(primaryTorpedoStore).isEmpty();
    verify(secondaryTorpedoStore).isEmpty();
  }

  @Test
  public void fireTorpedo_Single_ShouldOnlyFirePrimaryInCaseOfStoreFailureInItsInitialState () {
    // Arrange
    // As-per the test plan, this test case requires a newly constructed instance
    this.ship = new GT4500(
      this.primaryTorpedoStore,
      this.secondaryTorpedoStore
    );
    when (primaryTorpedoStore.isEmpty()).thenReturn(false);
    when (primaryTorpedoStore.fire(anyInt())).thenReturn(false);

    // Act
    // Fire 3 times
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryTorpedoStore, times(1)).fire(1);
    verify(secondaryTorpedoStore, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_ShouldFireSecondaryEvenIfPrimaryIsInFailure () {
    // Arrange
    // As-per the test plan, this test case requires a newly constructed instance
    this.ship = new GT4500(
      this.primaryTorpedoStore,
      this.secondaryTorpedoStore
    );
    when (primaryTorpedoStore.isEmpty()).thenReturn(false);
    when (secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when (primaryTorpedoStore.fire(anyInt())).thenReturn(false);
    when (secondaryTorpedoStore.fire(anyInt())).thenReturn(true);
    when (primaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when (secondaryTorpedoStore.getTorpedoCount()).thenReturn(20);

    // Act
    // Fire 3 times
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore).fire(10);
    verify(secondaryTorpedoStore).fire(20);
  }

  @Test
  public void fireTorpedo_Single_ShouldAlwaysFireSecondaryIfPrimaryIsEmpty () {
    // Arrange
    // Create a new instance of the ship for this test case
    this.ship = new GT4500(
      this.primaryTorpedoStore,
      this.secondaryTorpedoStore
    );
    when (primaryTorpedoStore.isEmpty()).thenReturn(true);
    when (secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when (primaryTorpedoStore.fire(anyInt())).thenReturn(false);
    when (secondaryTorpedoStore.fire(anyInt())).thenReturn(true);

    // Act
    // Fire 10 times
    boolean result = true;
    for (int i = 0; i < 10; i++) {
      result &= ship.fireTorpedo(FiringMode.SINGLE);
    }

    // Assert
    assertEquals(true, result);
    // Should not try to fire an empty store
    verify(primaryTorpedoStore, never()).fire(anyInt());
    // Should have fired secondary 10 times
    verify(secondaryTorpedoStore, times(10)).fire(1);
  }

}
