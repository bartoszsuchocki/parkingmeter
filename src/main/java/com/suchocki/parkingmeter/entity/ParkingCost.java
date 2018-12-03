package com.suchocki.parkingmeter.entity;

public class ParkingCost {

	private int id; // primary key in database
	private Currency currency;

	private Double firstHourValue;
	private Double secondHOurValue;
	private Double overTwoHoursMultiplier;

	public ParkingCost() {
		;
	}

	public ParkingCost(Currency currency) {
		this.currency = currency;
	}

	public ParkingCost(Currency currency, Double firstHourValue, Double secondHOurValue,
			Double overTwoHoursMultiplier) {
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

	public Double getFirstHourValue() {
		return firstHourValue;
	}

	public void setFirstHourValue(Double firstHourValue) {
		this.firstHourValue = firstHourValue;
	}

	public Double getSecondHOurValue() {
		return secondHOurValue;
	}

	public void setSecondHOurValue(Double secondHOurValue) {
		this.secondHOurValue = secondHOurValue;
	}

	public Double getOverTwoHoursMultiplier() {
		return overTwoHoursMultiplier;
	}

	public void setOverTwoHoursMultiplier(Double overTwoHoursMultiplier) {
		this.overTwoHoursMultiplier = overTwoHoursMultiplier;
	}

}
