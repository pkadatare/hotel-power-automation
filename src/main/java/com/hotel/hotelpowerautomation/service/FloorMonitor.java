package com.hotel.hotelpowerautomation.service;


import static com.hotel.hotelpowerautomation.model.Device.isDeviceOnMoreThanAMinute;
import static com.hotel.hotelpowerautomation.util.Constants.AC_UNIT;
import static com.hotel.hotelpowerautomation.util.Constants.LIGHT_UNIT;

import com.hotel.hotelpowerautomation.model.Activity;
import com.hotel.hotelpowerautomation.model.AirConditioner;
import com.hotel.hotelpowerautomation.model.Device;
import com.hotel.hotelpowerautomation.model.Floor;
import com.hotel.hotelpowerautomation.model.Hotel;
import com.hotel.hotelpowerautomation.model.Light;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.TimerTask;
import java.util.function.IntBinaryOperator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class FloorMonitor extends TimerTask implements Observer {

  private Hotel hotel;
  public static final IntBinaryOperator maxUnitsAllowed = (mainCorridors, subCorridors) ->
      (mainCorridors * 15) + (subCorridors * 10);

  /**
   * This method runs every 5 seconds and switch off the light which is on more than 1 min and turn
   * on any random AC which was switched off.
   */
  public void run() {
    this.hotel.getFloors().values()
        .forEach(floor ->
            floor.getSubCorridorMap().values()
                .forEach(corridor ->
                    corridor.getDevices().stream()
                        .filter(
                            device -> device instanceof Light && isDeviceOnMoreThanAMinute(device))
                        .forEach(device -> updateDevices(floor, device)
                        ))
        );
  }

  @Override
  public void update(Observable o, Object movement) {
    Activity activity = (Activity) movement;
    Floor floor = hotel.getFloors().get(activity.getFloorIndex());
    changeFloorState(floor, activity);
    hotel.print();
  }

  void updateDevices(Floor floor, Device device) {
    device.setState(false);
    if (isEnoughPowerToEnableAc(floor)) {
      setRandomACState(floor, true);
    }
    hotel.print();
  }

  private void setRandomACState(Floor floor, boolean state) {
    Optional<Device> randomAC = floor.getSubCorridorMap().values().stream()
        .flatMap(corridor -> corridor.getDevices().stream())
        .filter(device -> !device.isState() == state && device instanceof AirConditioner).findAny();
    if (randomAC.isPresent()) {
      randomAC.get().setState(state);
    }
  }

  private void changeFloorState(Floor floor, Activity activity) {
    if (activity.isMovement() && isNotEnoughPowerToEnableLight(floor)) {
      setRandomACState(floor, false);
    }
    floor.getSubCorridorMap().get(activity.getSubCorridorIndex())
        .updateDeviceState(activity.isMovement());

    if(isEnoughPowerToEnableAc(floor)) {
      Optional<Device> floorSpecificAc = floor.getSubCorridorMap().get(activity.getSubCorridorIndex())
          .getDevices()
          .stream()
          .filter(device -> device instanceof AirConditioner || !device.isState())
          .findFirst();
      if(floorSpecificAc.isPresent()){
        floorSpecificAc.get().setState(true);
      }


    }
  }

  public static boolean isNotEnoughPowerToEnableLight(Floor floor) {
    return (floor.getTotalPowerConsumed() + LIGHT_UNIT) > maxUnitsAllowed
        .applyAsInt(floor.getMainCorridorMap().size(), floor.getSubCorridorMap().size());
  }

  public static boolean isEnoughPowerToEnableAc(Floor floor) {
    return floor.getTotalPowerConsumed() + AC_UNIT <= maxUnitsAllowed
        .applyAsInt(floor.getMainCorridorMap().size(), floor.getSubCorridorMap().size());
  }

}
