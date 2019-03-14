package com.suchocki.parkingmeter.restcontrollertest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.ParkAction;

public class StopParkingMeterTest extends ParkingRestControllerTest {

	@Autowired
	private MockMvc mvc;

	private ParkAction parkingRegularDriverParkAction;
	private ParkAction parkingDisabledDriverParkAction;

	@Test
	public void shouldStopParkingMeterRegularDriver() throws Exception {
		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		mvc.perform(
				put("/api/stop").contentType(MediaType.APPLICATION_JSON_UTF8).content(regularDriverAlreadyParkingJSON))
				.andExpect(jsonPath("$[0].fee", is(10.50))).andExpect(status().isOk());
	}

	@Test
	public void shouldStopParkingMeterDisabledDriver() throws Exception {
		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingDisabledDriverParkAction = new ParkAction(threeHoursAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		mvc.perform(
				put("/api/stop").contentType(MediaType.APPLICATION_JSON_UTF8).content(disabledDriverAlreadyParkingJSON))
				.andExpect(jsonPath("$[0].fee", is(7.28))).andExpect(status().isOk());
	}
}
