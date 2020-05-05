package com.hotel.hotelpowerautomation.model;

import static com.hotel.hotelpowerautomation.util.Constants.AC_UNIT;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirConditioner implements Device {

  private boolean state;
  private LocalDateTime lastActivityTime;

  public AirConditioner() {
    this.state = true;
  }

  public AirConditioner(boolean state) {
    this.state = state;
  }

  @Override
  public int units() {
    return AC_UNIT;
  }

  @Override
  public String toString() {
    return " AC : " + getStatus(state);
  }


}
