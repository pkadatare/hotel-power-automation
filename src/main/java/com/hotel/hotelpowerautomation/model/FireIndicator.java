package com.hotel.hotelpowerautomation.model;

import static com.hotel.hotelpowerautomation.util.Constants.FIRE_INDICATOR_UNIT;

import java.time.LocalDateTime;

public class FireIndicator implements Device{

  private boolean state;

  public FireIndicator(boolean state) {
    this.state = state;
  }

  @Override
  public int units() {
    return FIRE_INDICATOR_UNIT;
  }

  @Override
  public boolean isState() {
    return state;
  }

  @Override
  public void setState(boolean state) {
    this.state =state;
  }

  @Override
  public void setLastActivityTime(LocalDateTime localDateTime) {

  }

  @Override
  public LocalDateTime getLastActivityTime() {
    return null;
  }

  @Override
  public String toString() {
    return " Fire Indicator : " + getStatus(state);
  }
}
