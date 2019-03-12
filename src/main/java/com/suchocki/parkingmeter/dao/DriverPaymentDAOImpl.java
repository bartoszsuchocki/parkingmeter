package com.suchocki.parkingmeter.dao;

import java.util.Calendar;
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
		driverPayment.setPayDate(new Date());
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
	public List<DriverPayment> getByDay(Date date) {
		Predicate<DriverPayment> predicate = payment -> datesEqualByDay(payment.getPayDate(), date);
		return FakeDatabaseStub.driverPayments.stream().filter(predicate).collect(Collectors.toList());
	}

	private boolean datesEqualByDay(Date date1, Date date2) {
		return getDateWithoutTimePart(date1).equals(getDateWithoutTimePart(date2));
	}

	private Date getDateWithoutTimePart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

}
