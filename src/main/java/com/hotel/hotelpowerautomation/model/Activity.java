package com.hotel.hotelpowerautomation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

  private int floorIndex;
  private int subCorridorIndex;
  private boolean movement;

}
