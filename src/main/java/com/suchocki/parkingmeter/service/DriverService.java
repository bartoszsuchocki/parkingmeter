package com.suchocki.parkingmeter.service;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

public interface DriverService {
	public ParkAction startParkingMeter(Driver driver);

	public ParkAction stopParkingMeter(Driver driver);

	public boolean startedParkingMeter(String licensePlate);
}
