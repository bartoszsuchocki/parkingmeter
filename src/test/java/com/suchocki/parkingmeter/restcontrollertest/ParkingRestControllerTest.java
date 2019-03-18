package com.suchocki.parkingmeter.restcontrollertest;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suchocki.parkingmeter.ParkingmeterApplication;
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
	protected static Currency plnCurrency;

	protected static ParkingCost regularPLNCost;
	protected static ParkingCost disabledPLNCost;

	protected static DriverType regularType;
	protected static DriverType disabledType;

	protected static Driver regularDriver;
	protected static Driver disabledDriver;
	protected static Driver regularDriverAlreadyParking;
	protected static Driver disabledDriverAlreadyParking;

	protected static String regularDriverJSON;
	protected static String disabledDriverJSON;
	protected static String regularDriverAlreadyParkingJSON;
	protected static String disabledDriverAlreadyParkingJSON;

	private static ObjectMapper mapper;

	@Autowired
	protected FakeDatabaseStub database;

	@BeforeClass
	public static void prepareObjects() throws JsonProcessingException {
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

		regularDriverJSON = mapper.writeValueAsString(regularDriver);
		disabledDriverJSON = mapper.writeValueAsString(disabledDriver);
		regularDriverAlreadyParkingJSON = mapper.writeValueAsString(regularDriverAlreadyParking);
		disabledDriverAlreadyParkingJSON = mapper.writeValueAsString(disabledDriverAlreadyParking);
	}

	@Before
	public void prepareFakeDatabase() {
		database.save(plnCurrency);

		database.save(regularDriver);
		database.save(disabledDriver);
		database.save(regularDriverAlreadyParking);
		database.save(disabledDriverAlreadyParking);
	}

	@After
	public void clearFakeDatabase() {
		database.clear(Currency.class);
		database.clear(Driver.class);
		database.clear(DriverPayment.class);
		database.clear(ParkAction.class);
	}
}
