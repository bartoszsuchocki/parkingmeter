package com.suchocki.parkingmeter.restcontrollertest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.suchocki.parkingmeter.ParkingmeterApplication;
import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.ParkAction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingmeterApplication.class)
@AutoConfigureMockMvc

public class ChargesFromURLCalculationTest extends ParkingRestControllerTest {

	@Autowired
	private MockMvc mvc;

	private ParkAction parkingRegularDriverParkAction;
	private ParkAction parkingDisabledDriverParkAction;

	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForRegularDriver() throws Exception {
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, regularDriverAlreadyParking);
		database.save(parkingRegularDriverParkAction);

		mvc.perform(get("/api/charge/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(10.5)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForRegularDriver() throws Exception {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingRegularDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, regularDriverAlreadyParking);
		database.save(parkingRegularDriverParkAction);

		mvc.perform(get("/api/charge/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(3.00)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForRegularDriver() throws Exception {
		LocalDateTime fiftyNineMinutesAgo = LocalDateTime.now().minusMinutes(59);
		parkingRegularDriverParkAction = new ParkAction(fiftyNineMinutesAgo, regularDriverAlreadyParking);
		database.save(parkingRegularDriverParkAction);

		mvc.perform(get("/api/charge/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(1.00)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForDisabledDriver() throws Exception {
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingDisabledDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, disabledDriverAlreadyParking);
		database.save(parkingDisabledDriverParkAction);

		mvc.perform(get("/api/charge/" + disabledDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(7.28)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForDisabledDriver() throws Exception {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, disabledDriverAlreadyParking);
		database.save(parkingDisabledDriverParkAction);

		mvc.perform(get("/api/charge/" + disabledDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(2.00)));
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForDisabledDriver() throws Exception {
		LocalDateTime ThirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(ThirtyMinutesAgo, disabledDriverAlreadyParking);
		database.save(parkingDisabledDriverParkAction);

		mvc.perform(get("/api/charge/" + disabledDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(0.00)));
	}
}
