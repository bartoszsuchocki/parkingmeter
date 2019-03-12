package com.suchocki.parkingmeter.dao;

import java.util.Optional;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

public interface ParkActionDAO extends DAO<ParkAction, Integer> {
	public Optional<ParkAction> getDriverLastParkAction(Driver driver);
}
