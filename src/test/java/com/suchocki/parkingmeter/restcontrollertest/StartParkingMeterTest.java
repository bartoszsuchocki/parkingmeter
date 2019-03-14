package com.suchocki.parkingmeter.restcontrollertest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class StartParkingMeterTest extends ParkingRestControllerTest{
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void shouldStartParkingMeterRegularDriver() throws Exception {

		mvc.perform(post("/api/start").contentType(MediaType.APPLICATION_JSON_UTF8).content(regularDriverJSON))
				.andExpect(jsonPath("$.driver.licensePlate", is(regularDriver.getLicensePlate())))
				.andExpect(jsonPath("$.end").isEmpty()).andExpect(status().isOk());
	}

	@Test
	public void shouldStartParkingMeterDisabledDriver() throws Exception {

		mvc.perform(post("/api/start").contentType(MediaType.APPLICATION_JSON_UTF8).content(disabledDriverJSON))
				.andExpect(jsonPath("$.driver.licensePlate", is(disabledDriver.getLicensePlate())))
				.andExpect(jsonPath("$.end").isEmpty()).andExpect(status().isOk());
	}
}
