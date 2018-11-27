package com.suchocki.parkingmeter.service;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

public interface ParkActionService extends Service<ParkAction, Integer> {
	public ParkAction getDriverLastParkAction(Driver driver);

	public ParkAction finishParkAction(Driver driver);
}
