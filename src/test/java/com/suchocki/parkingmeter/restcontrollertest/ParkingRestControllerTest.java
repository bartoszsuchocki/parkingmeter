package com.suchocki.parkingmeter.restcontrollertest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suchocki.parkingmeter.ParkingmeterApplication;
import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Currency;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverType;
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
	@AfterClass
	public static void clearFakeDatabase() {
		FakeDatabaseStub.currencies.clear();
		FakeDatabaseStub.drivers.clear();
		FakeDatabaseStub.parkActions.clear();
		FakeDatabaseStub.driverPayments.clear();
	}
}
