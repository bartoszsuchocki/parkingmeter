package com.suchocki.parkingmeter.entity;

import java.math.BigDecimal;

public class DriverCharge {

	protected Currency currency;
	protected BigDecimal fee;

	public DriverCharge() {
		;
	}

	public DriverCharge(Currency currency, BigDecimal fee) {
		this.currency = currency;
		this.fee = fee;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

}
