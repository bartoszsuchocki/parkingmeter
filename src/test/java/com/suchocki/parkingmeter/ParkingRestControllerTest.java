package com.suchocki.parkingmeter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Currency;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.DriverType;
import com.suchocki.parkingmeter.entity.ParkAction;
import com.suchocki.parkingmeter.entity.ParkingCost;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingmeterApplication.class)
@AutoConfigureMockMvc

public class ParkingRestControllerTest {

	@Autowired
	private MockMvc mvc;

	private static Currency plnCurrency;

	private static ParkingCost regularPLNCost;
	private static ParkingCost disabledPLNCost;

	private static DriverType regularType;
	private static DriverType disabledType;

	private static Driver regularDriver;
	private static Driver disabledDriver;
	private static Driver regularDriverAlreadyParking;
	private static Driver disabledDriverAlreadyParking;

	private static String regularDriverJSON;
	private static String disabledDriverJSON;
	private static String regularDriverAlreadyParkingJSON;
	private static String disabledDriverAlreadyParkingJSON;

	private static ParkAction parkingRegularDriverParkAction;
	private static ParkAction parkingDisabledDriverParkAction;

	private static ObjectMapper mapper;

	@BeforeClass
	public static void prepareObjects() {
		plnCurrency = new Currency("zloty", "PLN");

		regularPLNCost = new ParkingCost(plnCurrency, 1D, 2D, 1.5);
		disabledPLNCost = new ParkingCost(plnCurrency, 0D, 2D, 1.2);

		regularType = new DriverType("regular");
		regularType.addCost(regularPLNCost);

		disabledType = new DriverType("disabled");
		disabledType.addCost(disabledPLNCost);

		regularDriver = new Driver("wf22222", regularType);
		disabledDriver = new Driver("wf11111", disabledType);
		regularDriverAlreadyParking = new Driver("wf33333", regularType);
		disabledDriverAlreadyParking = new Driver("wf44444", disabledType);

		mapper = new ObjectMapper();

		try {
			regularDriverJSON = mapper.writeValueAsString(regularDriver);
			disabledDriverJSON = mapper.writeValueAsString(disabledDriver);
			regularDriverAlreadyParkingJSON = mapper.writeValueAsString(regularDriverAlreadyParking);
			disabledDriverAlreadyParkingJSON = mapper.writeValueAsString(disabledDriverAlreadyParking);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void prepareFakeDatabase() {
		FakeDatabaseStub.currencies.clear();
		FakeDatabaseStub.drivers.clear();
		FakeDatabaseStub.parkActions.clear();
		FakeDatabaseStub.driverPayments.clear();

		FakeDatabaseStub.currencies.add(plnCurrency);

		FakeDatabaseStub.drivers.add(regularDriver);
		FakeDatabaseStub.drivers.add(disabledDriver);
		FakeDatabaseStub.drivers.add(regularDriverAlreadyParking);
		FakeDatabaseStub.drivers.add(disabledDriverAlreadyParking);

	}

	/*------------starting parking meter------------*/
	@Test
	public void shouldStartParkingMeterRegularDriver() {

		try {
			mvc.perform(post("/api/start").contentType(MediaType.APPLICATION_JSON_UTF8).content(regularDriverJSON))
					.andExpect(jsonPath("$.driver.licensePlate", is(regularDriver.getLicensePlate())))
					.andExpect(jsonPath("$.end").isEmpty()).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void shouldStartParkingMeterDisabledDriver() {

		try {
			mvc.perform(post("/api/start").contentType(MediaType.APPLICATION_JSON_UTF8).content(disabledDriverJSON))
					.andExpect(jsonPath("$.driver.licensePlate", is(disabledDriver.getLicensePlate())))
					.andExpect(jsonPath("$.end").isEmpty()).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*------------end of starting parking meter------------*/

	/*------------stopping parking meter------------*/
	@Test
	public void shouldStopParkingMeterRegularDriver() {
		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(put("/api/stop").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(regularDriverAlreadyParkingJSON)).andExpect(jsonPath("$[0].fee", is(10.50)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldStopParkingMeterDisabledDriver() {
		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingDisabledDriverParkAction = new ParkAction(threeHoursAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		try {
			mvc.perform(put("/api/stop").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(disabledDriverAlreadyParkingJSON)).andExpect(jsonPath("$[0].fee", is(7.28)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*------------end of stopping parking meter------------*/

	/*------------checking if driver started parking meter------------*/
	@Test
	public void shouldReturnFalseCheckingIfDriverStartedParkingmeter() {
		try {
			mvc.perform(get("/api/check/" + regularDriver.getLicensePlate())).andExpect(status().isOk())
					.andExpect(content().string("false"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnTrueCheckingIfDriverStartedParkingmeter() {

		LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(get("/api/check/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
					.andExpect(content().string("true"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*------------ end of checking if driver started parking meter------------*/

	/*------------calculating charges------------*/
	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForRegularDriver() {
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(get("/api/charge/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(10.5)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForRegularDriver() {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingRegularDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(get("/api/charge/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(3.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForRegularDriver() {
		LocalDateTime fiftyNineMinutesAgo = LocalDateTime.now().minusMinutes(59);
		parkingRegularDriverParkAction = new ParkAction(fiftyNineMinutesAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(get("/api/charge/" + regularDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(1.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForDisabledDriver() {
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingDisabledDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		try {
			mvc.perform(get("/api/charge/" + disabledDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(7.28)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForDisabledDriver() {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		try {
			mvc.perform(get("/api/charge/" + disabledDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(2.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForDisabledDriver() {
		LocalDateTime ThirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(ThirtyMinutesAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		try {
			mvc.perform(get("/api/charge/" + disabledDriverAlreadyParking.getLicensePlate())).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(0.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*------------end of calculating charges by licensePlate------------*/

	/*------------calculating charges by driver JSON object------------*/
	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForRegularDriverByJSON() {
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingRegularDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(regularDriverAlreadyParkingJSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(10.5)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForRegularDriverByJSON() {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingRegularDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(regularDriverAlreadyParkingJSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(3.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForRegularDriverByJSON() {
		LocalDateTime fiftyNineMinutesAgo = LocalDateTime.now().minusMinutes(59);
		parkingRegularDriverParkAction = new ParkAction(fiftyNineMinutesAgo, regularDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingRegularDriverParkAction);

		try {
			mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(regularDriverAlreadyParkingJSON)).andExpect(status().isOk()).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(1.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow4hoursForDisabledDriverByJSON() {
		LocalDateTime threeHoursAnd20MinutesAgo = LocalDateTime.now().minusHours(3).minusMinutes(20);
		parkingDisabledDriverParkAction = new ParkAction(threeHoursAnd20MinutesAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		try {
			mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(disabledDriverAlreadyParkingJSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(7.28)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow2hoursForDisabledDriverByJSON() {
		LocalDateTime oneHourAnd30MinutesAgo = LocalDateTime.now().minusHours(1).minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(oneHourAnd30MinutesAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		try {
			mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(disabledDriverAlreadyParkingJSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(2.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturnDriverChargeForParkingTillNow1hourForDisabledDriverByJSON() {
		LocalDateTime ThirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
		parkingDisabledDriverParkAction = new ParkAction(ThirtyMinutesAgo, disabledDriverAlreadyParking);
		FakeDatabaseStub.parkActions.add(parkingDisabledDriverParkAction);

		try {
			mvc.perform(get("/api/charge").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(disabledDriverAlreadyParkingJSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(0.00)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*------------ end of calculating charges by driver JSON object------------*/

	/*------------calculating earnings for given day------------*/
	@Test
	public void shouldReturnEarningsInPLNForTodayAfterAddingSeveralPaymentsToDatabase() {
		LocalDateTime now = LocalDateTime.now();
//		Date oneDayAgo = new Date(System.currentTimeMillis() - 24 * 3600 * 1000);
		LocalDateTime oneDayAgo = now.minusDays(1);
		FakeDatabaseStub.driverPayments.add(new DriverPayment(plnCurrency, new BigDecimal(3.00), now, regularDriver));
		FakeDatabaseStub.driverPayments.add(new DriverPayment(plnCurrency, new BigDecimal(5.00), now, regularDriver));
		FakeDatabaseStub.driverPayments.add(new DriverPayment(plnCurrency, new BigDecimal(7.45), now, regularDriver));
		FakeDatabaseStub.driverPayments
				.add(new DriverPayment(plnCurrency, new BigDecimal(3.00), oneDayAgo, regularDriver));

		FakeDatabaseStub.printDB();

		String stringDateNowWithoutTime = now.toLocalDate().toString();// new
																		// SimpleDateFormat("yyyy-MM-dd").format(now);
		try {
			mvc.perform(get("/api/earnings/" + stringDateNowWithoutTime)).andExpect(status().isOk())
					.andExpect(jsonPath("$[0].fee", is(15.45)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*------------end of calculating earning for given day------------*/
}
