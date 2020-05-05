package com.hotel.hotelpowerautomation.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SubCorridor implements Corridor {

  private int index;
  private List<Device> devices;

  public SubCorridor(int index, List<Device> deviceList) {
    this.index = index;
    this.devices = deviceList;
  }

  public static Corridor getSubCorridorInstance(int index) {
    List<Device> deviceList = new ArrayList<>();
    deviceList.add(new Light(index, false));
    deviceList.add(new AirConditioner(false));
    return new SubCorridor(index, deviceList);
  }

  @Override
  public int getTotalPowerConsumed() {
    return devices.stream().filter(Device::isState).mapToInt(Device::units).sum();
  }

  @Override
  public void updateDeviceState(boolean movement) {
    devices.stream().forEach(device -> {
      if (device instanceof Light) {
        device.setState(movement);
        device.setLastActivityTime(LocalDateTime.now());
      }
    });
  }

  @Override
  public String toString() {
    return "SubCorridor " + this.index + Device.print(devices);
  }


}
