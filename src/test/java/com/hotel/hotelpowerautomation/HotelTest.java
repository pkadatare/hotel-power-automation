package com.hotel.hotelpowerautomation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hotel.hotelpowerautomation.model.Activity;
import com.hotel.hotelpowerautomation.model.AirConditioner;
import com.hotel.hotelpowerautomation.model.Device;
import com.hotel.hotelpowerautomation.model.FireIndicator;
import com.hotel.hotelpowerautomation.model.Hotel;
import com.hotel.hotelpowerautomation.model.Light;
import com.hotel.hotelpowerautomation.service.FloorMonitor;
import com.hotel.hotelpowerautomation.service.MotionObservable;
import java.util.List;
import java.util.Timer;
import org.junit.jupiter.api.Test;

public class HotelTest {

  @Test()
  public void testHotelPrint() {
    Hotel hotel = new Hotel.HotelBuilder().floor(2).mainCorridors(1).subCorridors(2).build();
    hotel.print();
  }

  @Test()
  public void initializeFloor() {
    Hotel hotel = new Hotel.HotelBuilder().floor(2).build();
    assertEquals(2, hotel.getFloors().size());
  }

  @Test()
  public void initializeMainCorridor() {
    Hotel hotel = new Hotel.HotelBuilder().floor(1).mainCorridors(1).build();
    long totalOnDevices = hotel.getFloors().get(1).getMainCorridorMap().get(1).getDevices()
        .stream().filter(Device::isState).count();
    assertEquals(1, hotel.getFloors().get(1).getMainCorridorMap().size());
    assertEquals(totalOnDevices, 2);
  }

  @Test()
  public void initializeSubCorridor() {
    Hotel hotel = new Hotel.HotelBuilder().floor(1).subCorridors(1).build();
    long totalOnDevices = hotel.getFloors().get(1).getSubCorridorMap().get(1).getDevices()
        .stream().filter(Device::isState).count();
    assertEquals(1, hotel.getFloors().get(1).getSubCorridorMap().size());
    assertEquals(totalOnDevices, 1);
  }

  @Test()
  public void testMotionObserver() throws InterruptedException {
    Hotel hotel = new Hotel.HotelBuilder().floor(1).mainCorridors(1).subCorridors(2).build();
    hotel.print();
    FloorMonitor monitor = new FloorMonitor(hotel);
    MotionObservable observable = new MotionObservable(monitor);
    Timer timer = new Timer();
    timer.schedule(monitor, 0, 5000);
    observable.setActivity(new Activity(1, 2, true));
    observable.setActivity(new Activity(1, 2, false));
  }

  @Test()
  public void testHotelPrintForFireExitIndicator() {
    Hotel hotel = new Hotel.HotelBuilder().floor(1).mainCorridors(1).build();
    List<Device> devices = hotel.getFloors().get(1).getMainCorridorMap().get(1).getDevices();

    assertEquals(devices.size(), 3);

    assertThat(devices.get(0), instanceOf(Light.class));
    assertThat(devices.get(1), instanceOf(AirConditioner.class));
    assertThat(devices.get(2), instanceOf(FireIndicator.class));
    assertTrue(devices.get(2).isState());
  }

  @Test()
  public void testHotelPrintForFireExitIndicatorWithPowerConsumption() {
    Hotel hotel = new Hotel.HotelBuilder().floor(2).mainCorridors(1).subCorridors(2).build();

    // (1 * 15) + (2* 10)
    assertTrue(hotel.getFloors().get(1).getTotalPowerConsumed()<= 35);
  }


}
