package com.suchocki.parkingmeter.entity;

public class Driver {
	private String licensePlate; // id in database
	private DriverType driverType;

	// There could be some additional fields like name, surname etc.

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public DriverType getDriverType() {
		return driverType;
	}

	public void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}
}
