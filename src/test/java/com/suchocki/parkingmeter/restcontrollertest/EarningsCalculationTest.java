package com.suchocki.parkingmeter.restcontrollertest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.DriverPayment;

public class EarningsCalculationTest extends ParkingRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void shouldReturnEarningsInPLNForTodayAfterAddingSeveralPaymentsToDatabase() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneDayAgo = now.minusDays(1);
		FakeDatabaseStub.driverPayments.add(new DriverPayment(plnCurrency, new BigDecimal(3.00), now, regularDriver));
		FakeDatabaseStub.driverPayments.add(new DriverPayment(plnCurrency, new BigDecimal(5.00), now, regularDriver));
		FakeDatabaseStub.driverPayments.add(new DriverPayment(plnCurrency, new BigDecimal(7.45), now, regularDriver));
		FakeDatabaseStub.driverPayments
				.add(new DriverPayment(plnCurrency, new BigDecimal(3.00), oneDayAgo, regularDriver));

		FakeDatabaseStub.printDB();

		String stringDateNowWithoutTime = now.toLocalDate().toString();

		mvc.perform(get("/api/earnings/" + stringDateNowWithoutTime)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fee", is(15.45)));
	}

}
