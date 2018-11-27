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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Driver object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

}
