package com.suchocki.parkingmeter.service;

import java.util.List;

import com.suchocki.parkingmeter.entity.Currency;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.ParkAction;

public interface DriverService {
	public ParkAction startParkingMeter(Driver driver);

	public ParkAction stopParkingMeter(Driver driver);

	public boolean startedParkingMeter(String licensePlate);

	public List<DriverCharge> checkChargeForParkingTillNow(Driver driver);

	public List<DriverCharge> checkChargeForParkingTillNow(String licensePlate);

	public DriverPayment pay(DriverPayment payment);
}
