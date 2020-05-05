package com.hotel.hotelpowerautomation.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;


@Getter
public class MainCorridor implements Corridor {

  private int index;
  private List<Device> devices;

  public MainCorridor(int index, List<Device> devices) {
    this.index = index;
    this.devices = devices;
  }

  public static Corridor getMainCorridorInstance(int index) {
    List<Device> deviceList = new ArrayList<>();
    deviceList.add(new Light(index, true));
    deviceList.add(new AirConditioner());
    deviceList.add(new FireIndicator(true));
    return new MainCorridor(index, deviceList);
  }

  @Override
  public int getTotalPowerConsumed() {
    return devices.stream().filter(Device::isState).mapToInt(Device::units).sum();
  }

  @Override
  public void updateDeviceState(boolean movement) {
    throw new UnsupportedOperationException("Main Corridor state can't be changed");
  }

  @Override
  public String toString() {
    return "MainCorridor " + this.index + Device.print(devices);
  }
}
