package com.suchocki.parkingmeter.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.DriverPayment;

@Repository
public class DriverPaymentDAOImpl implements DriverPaymentDAO {

	@Override
	public void save(DriverPayment driverPayment) {
		driverPayment.setPayDate(LocalDateTime.now());
		driverPayment.setId(FakeDatabaseStub.driverPayments.size() + 1);
		FakeDatabaseStub.driverPayments.add(driverPayment);
	}

	@Override
	public Optional<DriverPayment> get(int id) {
		Predicate<DriverPayment> predicate = payment -> payment.getId() == id;
		return FakeDatabaseStub.driverPayments.stream().filter(predicate).findFirst();
	}

	@Override
	public List<DriverPayment> getAll() {
		return FakeDatabaseStub.driverPayments;
	}

	@Override
	public List<DriverPayment> getByDay(LocalDate date) {
		Predicate<DriverPayment> predicate = payment -> payment.getPayDate().toLocalDate().equals(date);
		return FakeDatabaseStub.driverPayments.stream().filter(predicate).collect(Collectors.toList());
	}

}
