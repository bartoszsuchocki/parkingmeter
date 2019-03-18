package com.suchocki.parkingmeter.restcontrollertest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.ParkAction;

public class ChargesFromJSONCalculationTest extends ParkingRestControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	protected FakeDatabaseStub database;

	private ParkAction parkingRegularDriverParkAction;
	private ParkAction parkingDisabledDriverParkAction;

	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForRegularDriverByJSON() throws Exception {
		database.printDB();
		
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, regularDriverAlreadyParking);
		database.save(parkingRegularDriverParkAction);

		mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(regularDriverAlreadyParkingJSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(10.5)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForRegularDriverByJSON() throws Exception {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingRegularDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, regularDriverAlreadyParking);
		database.save(parkingRegularDriverParkAction);

		mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(regularDriverAlreadyParkingJSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(3.00)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForRegularDriverByJSON() throws Exception {
		LocalDateTime fiftyNineMinutesAgo = LocalDateTime.now().minusMinutes(59);
		parkingRegularDriverParkAction = new ParkAction(fiftyNineMinutesAgo, regularDriverAlreadyParking);
		database.save(parkingRegularDriverParkAction);

		mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(regularDriverAlreadyParkingJSON)).andExpect(status().isOk()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(1.00)));

	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForDisabledDriverByJSON() throws Exception {
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingDisabledDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, disabledDriverAlreadyParking);
		database.save(parkingDisabledDriverParkAction);

		mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(disabledDriverAlreadyParkingJSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(7.28)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForDisabledDriverByJSON() throws Exception {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, disabledDriverAlreadyParking);
		database.save(parkingDisabledDriverParkAction);

		mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(disabledDriverAlreadyParkingJSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(2.00)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForDisabledDriverByJSON() throws Exception {
		LocalDateTime ThirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(ThirtyMinutesAgo, disabledDriverAlreadyParking);
		database.save(parkingDisabledDriverParkAction);

		mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(disabledDriverAlreadyParkingJSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(0.00)));
	}
}
