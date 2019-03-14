package com.suchocki.parkingmeter.restcontrollertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.ParkAction;

public class CheckingParkingMeterStartTest extends ParkingRestControllerTest {

	@Autowired
	private MockMvc mvc;

	private ParkAction parkingRegularDriverParkAction;
	private ParkAction parkingDisabledDriverParkAction;

	@Test
	public void shouldReturnFalseCheckingIfDriverStartedParkingmeter() throws Exception {

		mvc.perform(get("/api/check/" + regularDriver.getLicensePlate())).andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	public void shouldReturnTrueCheckingIfDriverStartedParkingmeter() throws Exception {

		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		mvc.perform(get("/api/check/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
				.andExpect(content().string("true"));
	}

}
