package com.hotel.hotelpowerautomation.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Device {

  int units();

  boolean isState();

  void setState(boolean state);

  void setLastActivityTime(LocalDateTime localDateTime);

  LocalDateTime getLastActivityTime();

  default String getStatus(boolean state) {
    return state ? "ON" : "OFF";
  }

  static String print(List<Device> deviceList) {
    return deviceList.stream()
        .map(Object::toString)
        .collect(Collectors.joining(" "));
  }

  static boolean isDeviceOnMoreThanAMinute(Device device) {
    return device.isState()
        && Objects.nonNull(device.getLastActivityTime())
        && device.getLastActivityTime().isBefore(LocalDateTime.now().minusMinutes(1));
  }
}
