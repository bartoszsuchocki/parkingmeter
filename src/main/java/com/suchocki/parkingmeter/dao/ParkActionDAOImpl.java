package com.suchocki.parkingmeter.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

@Repository
public class ParkActionDAOImpl implements ParkActionDAO {

	@Autowired
	private FakeDatabaseStub database;

	@Override
	public void save(ParkAction parkAction) {
		database.save(parkAction);
	}

	@Override
	public Optional<ParkAction> get(Integer id) {
		return database.get(id, ParkAction.class);
	}

	@Override
	public void update(ParkAction parkAction) {
		database.update(parkAction);
	}

	@Override
	public void delete(Integer id) {
		database.delete(id, ParkAction.class);
	}

	@Override
	public Optional<ParkAction> getDriverLastParkAction(Driver driver) {
		return database.getDriverLastParkAction(driver);
	}

}
