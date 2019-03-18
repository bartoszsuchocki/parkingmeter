package com.suchocki.parkingmeter.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Driver;

@Repository
public class DriverDAOImpl implements DriverDAO {

	@Autowired
	private FakeDatabaseStub database;

	@Override
	public void save(Driver driver) {
		database.save(driver);
	}

	@Override
	public Optional<Driver> get(String id) {
		return database.get(id, Driver.class);
	}

	@Override
	public void update(Driver driver) {
		database.update(driver);
	}

	@Override
	public void delete(String licensePlate) {
		database.delete(licensePlate, Driver.class);
	}

}
