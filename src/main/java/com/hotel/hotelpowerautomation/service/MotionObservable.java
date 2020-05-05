package com.hotel.hotelpowerautomation.service;

import com.hotel.hotelpowerautomation.model.Activity;
import java.util.Observable;

public class MotionObservable extends Observable {

  private FloorMonitor floorMonitor;

  public MotionObservable(FloorMonitor floorMonitor) {
    this.floorMonitor = floorMonitor;
    addFloorMonitorToObservers();
  }

  private void addFloorMonitorToObservers() {
    addObserver(this.floorMonitor);
  }

  public void setActivity(Activity activity) {
    setChanged();
    notifyObservers(activity);
  }

}
