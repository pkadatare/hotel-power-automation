package com.hotel.hotelpowerautomation.model;

import static com.hotel.hotelpowerautomation.model.Corridor.print;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
@Slf4j
public class Floor {

  private int index;
  private Map<Integer, Corridor> subCorridorMap = new HashMap<>();
  private Map<Integer, Corridor> mainCorridorMap = new HashMap<>();

  public Floor(int index) {
    this.index = index;
  }

  public static Floor getFloorInstance(int index) {
    return new Floor(index);
  }

  public int getTotalPowerConsumed() {
    return Corridor.getTotalPowerConsumed(subCorridorMap)
        + Corridor.getTotalPowerConsumed(mainCorridorMap);
  }

  @Override
  public String toString() {
    return "\nFloor " + index + "\n" + print(mainCorridorMap) + "\n" + print(subCorridorMap);
  }


}
