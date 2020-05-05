package com.hotel.hotelpowerautomation.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hotel.hotelpowerautomation.model.AirConditioner;
import com.hotel.hotelpowerautomation.model.Corridor;
import com.hotel.hotelpowerautomation.model.Device;
import com.hotel.hotelpowerautomation.model.Floor;
import com.hotel.hotelpowerautomation.model.Hotel;
import com.hotel.hotelpowerautomation.model.Light;
import com.hotel.hotelpowerautomation.model.MainCorridor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FloorMonitorTest {

  @Test()
  public void testIsNotEnoughPowerToEnableLight() {
    Floor floor = mock(Floor.class);
    Map<Integer, Corridor> corridorMap = new HashMap<>();
    corridorMap.put(1, new MainCorridor(1, Collections.EMPTY_LIST));

    when(floor.getTotalPowerConsumed()).thenReturn(20);
    when(floor.getMainCorridorMap()).thenReturn(corridorMap);
    when(floor.getSubCorridorMap()).thenReturn(corridorMap);

    boolean result = FloorMonitor.isNotEnoughPowerToEnableLight(floor);
    assertFalse(result);
  }

  @Test()
  public void testIsNotEnoughPowerToEnableLightForFalse() {
    Floor floor = mock(Floor.class);
    Map<Integer, Corridor> corridorMap = new HashMap<>();
    corridorMap.put(1, new MainCorridor(1, Collections.EMPTY_LIST));

    when(floor.getTotalPowerConsumed()).thenReturn(35);
    when(floor.getMainCorridorMap()).thenReturn(corridorMap);
    when(floor.getSubCorridorMap()).thenReturn(corridorMap);

    boolean result = FloorMonitor.isNotEnoughPowerToEnableLight(floor);
    assertTrue(result);
  }

  @Test()
  public void testIsEnoughPowerToEnableAc() {
    Floor floor = mock(Floor.class);
    Map<Integer, Corridor> corridorMap = new HashMap<>();
    corridorMap.put(1, new MainCorridor(1, Collections.EMPTY_LIST));

    when(floor.getTotalPowerConsumed()).thenReturn(20);
    when(floor.getMainCorridorMap()).thenReturn(corridorMap);
    when(floor.getSubCorridorMap()).thenReturn(corridorMap);

    boolean result = FloorMonitor.isEnoughPowerToEnableAc(floor);
    assertFalse(result);
  }

  @Test()
  public void testIsEnoughPowerToEnableAcForFalse() {
    Floor floor = mock(Floor.class);
    Map<Integer, Corridor> corridorMap = new HashMap<>();
    corridorMap.put(1, new MainCorridor(1, Collections.EMPTY_LIST));

    when(floor.getTotalPowerConsumed()).thenReturn(15);
    when(floor.getMainCorridorMap()).thenReturn(corridorMap);
    when(floor.getSubCorridorMap()).thenReturn(corridorMap);

    boolean result = FloorMonitor.isEnoughPowerToEnableAc(floor);
    assertTrue(result);
  }

  @Test()
  public void testForUpdateDevice() {
    Hotel hotel = new Hotel.HotelBuilder().floor(2).mainCorridors(1).subCorridors(1).build();
    Floor floor = hotel.getFloors().get(1);
    Device light = floor.getSubCorridorMap().get(1).getDevices().stream()
        .filter(device1 -> device1 instanceof Light)
        .findAny().get();
    Device ac = floor.getSubCorridorMap().get(1).getDevices().stream()
        .filter(device1 -> device1 instanceof AirConditioner)
        .findAny().get();
    light.setState(true);
    ac.setState(false);
    FloorMonitor fm = new FloorMonitor(hotel);
    fm.updateDevices(floor, light);
    assertFalse(light.isState());
    assertTrue(ac.isState());
  }

}