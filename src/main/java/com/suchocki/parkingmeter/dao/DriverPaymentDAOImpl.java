package com.suchocki.parkingmeter.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.DriverPayment;

@Repository
public class DriverPaymentDAOImpl implements DriverPaymentDAO {

	@Autowired
	private FakeDatabaseStub database;
	
	@Override
	public void save(DriverPayment driverPayment) {
		database.save(driverPayment);
	}

	@Override
	public Optional<DriverPayment> get(int id) {
		return database.get(id, DriverPayment.class);
	}

	@Override
	public List<DriverPayment> getByDay(LocalDate date) {
		return database.getDriverPaymentByDay(date);
	}

}
