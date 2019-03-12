package com.suchocki.parkingmeter.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Driver;

@Repository
public class DriverDAOImpl implements DriverDAO {

	@Override
	public void save(Driver driver) {
		if(get(driver.getLicensePlate()).isPresent()) {
			update(driver);
		}
		else{
			FakeDatabaseStub.drivers.add(driver);
		}
	}

	@Override
	public Optional<Driver> get(String id) {
		return FakeDatabaseStub.drivers.stream().filter(driver -> driver.getLicensePlate().equals(id)).findFirst();
	}

	@Override
	public List<Driver> getAll() {
		return FakeDatabaseStub.drivers;
	}

	@Override
	public void update(Driver driver) {
		Predicate<Driver> predicate = d -> driver.getLicensePlate().equals(d.getLicensePlate());
		Optional<Driver> toUpdate = FakeDatabaseStub.drivers.stream().filter(predicate).findFirst();
		toUpdate.ifPresent(d -> d.updateProperties(driver));
	}

	@Override
	public void delete(String licensePlate) {
		FakeDatabaseStub.drivers.removeIf(d -> d.getLicensePlate().equals(licensePlate));
	}

}
