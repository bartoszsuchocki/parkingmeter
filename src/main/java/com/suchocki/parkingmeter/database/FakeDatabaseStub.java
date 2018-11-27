package com.suchocki.parkingmeter.database;

import java.util.ArrayList;
import java.util.List;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

public final class FakeDatabaseStub {
	public static List<ParkAction> parkActions = new ArrayList<>();
	public static List<Driver> drivers = new ArrayList<>();
}
