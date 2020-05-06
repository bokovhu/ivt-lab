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

}
