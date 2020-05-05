package com.hotel.hotelpowerautomation.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Corridor {

  List<Device> getDevices();

  int getTotalPowerConsumed();

  void updateDeviceState(boolean movement);

  static String print(Map<Integer, Corridor> corridorMap) {
    return corridorMap.values().stream()
        .map(Object::toString)
        .collect(Collectors.joining("\n"));
  }

  /**
   * This static method will return total power consumer for specified corridor map
   *
   * @param corridorMap
   * @return
   */
  static int getTotalPowerConsumed(Map<Integer, Corridor> corridorMap) {
    return corridorMap.values().stream()
        .mapToInt(Corridor::getTotalPowerConsumed).sum();
  }


}
