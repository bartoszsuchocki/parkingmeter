package com.suchocki.parkingmeter.service;

import java.util.Date;
import java.util.List;

import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;

public interface DriverPaymentService { //not extending Service, because payments should not be updated
	public void save(DriverPayment driverPayment);

	public DriverPayment get(int id);

	public List<DriverPayment> getAll();

	public List<DriverCharge> getPaymentsSumByday(Date date);

	public List<DriverPayment> getByDay(Date date);
}
