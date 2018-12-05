package com.suchocki.parkingmeter.database;

import java.util.ArrayList;
import java.util.List;

import com.suchocki.parkingmeter.entity.Currency;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.ParkAction;

public final class FakeDatabaseStub {
	public static List<ParkAction> parkActions = new ArrayList<>();
	public static List<Driver> drivers = new ArrayList<>();
	public static List<DriverPayment> driverPayments = new ArrayList<>();
	public static List<Currency> currencies = new ArrayList<>();
	
	public static void printDB() {
		System.out.println("------------Database print--------------");

		System.out.println(FakeDatabaseStub.currencies);

		System.out.println("-----------End of database print---------");
	}
}
