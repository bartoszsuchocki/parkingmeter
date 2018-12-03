package com.suchocki.parkingmeter.service;

import java.util.Date;
import java.util.List;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.ParkAction;

public interface ParkActionService extends Service<ParkAction, Integer> {
	public ParkAction getDriverLastParkAction(Driver driver);

	public ParkAction finishParkAction(Driver driver);

	public DriverCharge getChargeForADay(Date date);
}
