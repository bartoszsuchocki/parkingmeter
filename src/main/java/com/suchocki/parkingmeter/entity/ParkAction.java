package com.suchocki.parkingmeter.entity;

import java.util.Date;

public class ParkAction {
	private Date start;
	private Date end;
	private Driver driver;

	public ParkAction() {

	}

	public ParkAction(Date start, Driver driver) {
		this.start = start;
		this.driver = driver;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
