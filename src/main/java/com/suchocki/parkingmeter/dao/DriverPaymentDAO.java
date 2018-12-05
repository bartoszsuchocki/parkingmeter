package com.suchocki.parkingmeter.dao;

import java.util.Date;
import java.util.List;

import com.suchocki.parkingmeter.entity.DriverPayment;

public interface DriverPaymentDAO{
	public void save(DriverPayment driverPayment);

	public DriverPayment get(int id);

	public List<DriverPayment> getAll();

	public List<DriverPayment> getByDay(Date date);
}
