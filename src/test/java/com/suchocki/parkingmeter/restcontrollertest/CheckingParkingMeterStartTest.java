package com.suchocki.parkingmeter.restcontrollertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.suchocki.parkingmeter.entity.ParkAction;

public class CheckingParkingMeterStartTest extends ParkingRestControllerTest {

	@Autowired
	private MockMvc mvc;

	private ParkAction parkingRegularDriverParkAction;
	private ParkAction parkingDisabledDriverParkAction;

	@Test
	public void shouldReturnFalseCheckingIfRegularDriverStartedParkingmeter() throws Exception {

		mvc.perform(get("/api/park-actions/" + regularDriver.getLicensePlate())).andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	public void shouldReturnTrueCheckingIfRegularDriverStartedParkingmeter() throws Exception {

		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAgo, regularDriverAlreadyParking);
		database.save(parkingRegularDriverParkAction);

		mvc.perform(get("/api/park-actions/" + regularDriverAlreadyParking.getLicensePlate()))
				.andExpect(status().isOk()).andExpect(content().string("true"));
	}

	@Test
	public void shouldReturnFalseCheckingIfDisabledDriverStartedParkingmeter() throws Exception {

		mvc.perform(get("/api/park-actions/" + disabledDriver.getLicensePlate())).andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	public void shouldReturnTrueCheckingIfDisableddDriverStartedParkingmeter() throws Exception {

		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingDisabledDriverParkAction = new ParkAction(threeHoursAgo, disabledDriverAlreadyParking);
		database.save(parkingDisabledDriverParkAction);

		mvc.perform(get("/api/park-actions/" + disabledDriverAlreadyParking.getLicensePlate()))
				.andExpect(status().isOk()).andExpect(content().string("true"));
	}

}
