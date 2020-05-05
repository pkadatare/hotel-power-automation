package com.hotel.hotelpowerautomation;

import com.hotel.hotelpowerautomation.model.Activity;
import com.hotel.hotelpowerautomation.model.Hotel;
import com.hotel.hotelpowerautomation.service.FloorMonitor;
import com.hotel.hotelpowerautomation.service.MotionObservable;
import java.util.Timer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HotelPowerAutomationApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test()
	public void applicationDriverTest() throws InterruptedException {
		Hotel hotel = new Hotel.HotelBuilder().floor(1).mainCorridors(1).subCorridors(2).build();
		hotel.print();

		FloorMonitor monitor = new FloorMonitor(hotel);
		Timer timer = new Timer(true);
		timer.schedule(monitor, 0, 5000);
		//Delayed executer which work with lambda
		MotionObservable observable = new MotionObservable(monitor);

		//movement on 1 floor 2 corridor
		observable.setActivity(new Activity(  1, 2, true));

//		//Delay of 1.5 minutes
//		Thread.sleep(90000);
//
//		//no movement on 1 floor 2 corridor
//		observable.setActivity(new Activity(  1, 2, false));
	}

}
