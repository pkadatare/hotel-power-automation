package com.hotel.hotelpowerautomation.model;

import static com.hotel.hotelpowerautomation.util.Constants.LIGHT_UNIT;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Light implements Device {

  private int index;
  private boolean state;
  private LocalDateTime lastActivityTime;

  public Light(int index, boolean state) {
    this.index = index;
    this.state = state;
  }

  @Override
  public int units() {
    return LIGHT_UNIT;
  }

  @Override
  public String toString() {
    return " Light " + index + " : " + getStatus(state);
  }
}
