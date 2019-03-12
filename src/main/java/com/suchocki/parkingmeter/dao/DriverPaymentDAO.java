package com.suchocki.parkingmeter.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.suchocki.parkingmeter.entity.DriverPayment;

public interface DriverPaymentDAO { // not extending DAO, because payments should not be updated
	public void save(DriverPayment driverPayment);

	public Optional<DriverPayment> get(int id);

	public List<DriverPayment> getAll();

	public List<DriverPayment> getByDay(Date date);
}
