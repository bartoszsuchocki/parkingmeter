package com.suchocki.parkingmeter.dao;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

public interface ParkActionDAO extends DAO<ParkAction, Integer> {
	public ParkAction getDriverLastParkAction(Driver driver);
}
