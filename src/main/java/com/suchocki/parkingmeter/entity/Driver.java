package com.suchocki.parkingmeter.entity;

public class Driver {
	private String licensePlate; // primary key in database
	private DriverType driverType;

	public Driver() {
	}

	public Driver(String licensePlate, DriverType driverType) {
		this.licensePlate = licensePlate;
		this.driverType = driverType;
	}

	// There could be some additional fields like name, surname etc.

	public void updateProperties(Driver otherDriver) {
		this.licensePlate = otherDriver.getLicensePlate();
		this.driverType = otherDriver.getDriverType();
	}

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

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Driver)) {
			return false;
		}
		Driver otherDriver = (Driver) object;
		if (otherDriver.getLicensePlate() == null || otherDriver.getDriverType() == null) {
			return false;
		}
		return otherDriver.getLicensePlate().equals(this.licensePlate)
				&& otherDriver.getDriverType().equals(this.driverType);
	}
}
