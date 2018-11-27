package com.suchocki.parkingmeter.entity;

import java.math.BigDecimal;

public class ParkingCost {

	private int id; // primary key in database
	private Currency currency;

	private BigDecimal firstHourValue;
	private BigDecimal secondHOurValue;
	private BigDecimal overTwoHoursMultiplier;

	public ParkingCost() {
		;
	}

	public ParkingCost(Currency currency) {
		this.currency = currency;
	}

	public ParkingCost(Currency currency, BigDecimal firstHourValue, BigDecimal secondHOurValue,
			BigDecimal overTwoHoursMultiplier) {
		this.currency = currency;
		this.firstHourValue = firstHourValue;
		this.secondHOurValue = secondHOurValue;
		this.overTwoHoursMultiplier = overTwoHoursMultiplier;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getFirstHourValue() {
		return firstHourValue;
	}

	public void setFirstHourValue(BigDecimal firstHourValue) {
		this.firstHourValue = firstHourValue;
	}

	public BigDecimal getSecondHOurValue() {
		return secondHOurValue;
	}

	public void setSecondHOurValue(BigDecimal secondHOurValue) {
		this.secondHOurValue = secondHOurValue;
	}

	public BigDecimal getOverTwoHoursMultiplier() {
		return overTwoHoursMultiplier;
	}

	public void setOverTwoHoursMultiplier(BigDecimal overTwoHoursMultiplier) {
		this.overTwoHoursMultiplier = overTwoHoursMultiplier;
	}

}
