package com.suchocki.parkingmeter.service;

import java.util.Optional;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

public interface ParkActionService extends Service<ParkAction, Integer> {
	public Optional<ParkAction> getDriverLastParkAction(Driver driver);

	public Optional<ParkAction> finishParkAction(Driver driver);
}
