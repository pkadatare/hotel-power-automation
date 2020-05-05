package com.hotel.hotelpowerautomation.model;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
@Slf4j
public class Hotel {

  private Map<Integer, Floor> floors;

  public Hotel(HotelBuilder hotelBuilder) {
    this.floors = hotelBuilder.getFloorMap();
  }

  public void print() {
    floors.values().stream().forEach(floor -> log.info(floor.toString()));
  }

  @Getter
  public static class HotelBuilder {

    private Map<Integer, Floor> floorMap;

    public HotelBuilder floor(int floors) {
      floorMap = IntStream.range(1, floors + 1).boxed()
          .collect(Collectors.toMap(Function.identity(), Floor::getFloorInstance));
      return this;
    }

    public HotelBuilder mainCorridors(int mainCorridors) {
      floorMap.values().stream().forEach(floor -> floor.setMainCorridorMap(
          IntStream.range(1, mainCorridors + 1).boxed()
              .collect(
                  Collectors.toMap(Function.identity(), MainCorridor::getMainCorridorInstance))));
      return this;
    }

    public HotelBuilder subCorridors(int subCorridors) {
      floorMap.values().stream().forEach(floor -> floor.setSubCorridorMap(
          IntStream.range(1, subCorridors + 1).boxed()
              .collect(
                  Collectors.toMap(Function.identity(), SubCorridor::getSubCorridorInstance))));
      return this;
    }

    public Hotel build() {
      return new Hotel(this);
    }
  }


}
