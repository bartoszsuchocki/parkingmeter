package com.suchocki.parkingmeter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Driver;

@Repository
public class DriverDAOImpl implements DriverDAO {

	@Override
	public void save(Driver driver) {
		FakeDatabaseStub.drivers.add(driver);
	}

	@Override
	public Driver get(String id) {
		for (Driver d : FakeDatabaseStub.drivers) {
			if (d.getLicensePlate().equals(id)) {
				return d;
			}
		}
		return null;
	}

	@Override
	public List<Driver> getAll() {
		return FakeDatabaseStub.drivers;
	}

	@Override
	public void update(Driver driver) {
		for (Driver d : FakeDatabaseStub.drivers) {
			if (d.getLicensePlate().equals(driver.getLicensePlate())) {
				d.updateProperties(driver);
			}
		}
	}

	@Override
	public void delete(String licensePlate) {
		int counter = 0;
		for (Driver d : FakeDatabaseStub.drivers) {
			if (d.getLicensePlate().equals(licensePlate)) {
				break;
			}
			counter++;
		}
		FakeDatabaseStub.drivers.remove(counter);
	}

}
