package com.hotel.hotelpowerautomation.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import org.junit.jupiter.api.Test;

class MainCorridorTest {

  @Test
  void shouldThrowException() {
    MainCorridor corridor = new MainCorridor(1, Collections.EMPTY_LIST);

    Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
      corridor.updateDeviceState(true);
    });
    assertEquals(exception.getMessage(), "Main Corridor state can't be changed");
  }
}