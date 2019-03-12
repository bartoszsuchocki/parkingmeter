package com.suchocki.parkingmeter.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DriverPayment extends DriverCharge {
	private int id;
	private Date payDate;
	private Driver driver;

	public DriverPayment() {
	}

	public DriverPayment(Currency currency, BigDecimal fee) {
		super(currency, fee);
	}

	public DriverPayment(Currency currency, BigDecimal fee, Date payDate, Driver driver) {
		super(currency, fee);
		this.payDate = payDate;
		this.driver = driver;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
