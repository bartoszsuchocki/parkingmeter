package com.suchocki.parkingmeter.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;

public interface DriverPaymentService { // not extending Service, because payments should not be updated
	public void save(DriverPayment driverPayment);

	public Optional<DriverPayment> get(int id);

	public List<DriverCharge> getPaymentsSumByday(LocalDate date);

	public List<DriverPayment> getByDay(LocalDate date);

}
