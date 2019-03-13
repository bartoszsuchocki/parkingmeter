package com.suchocki.parkingmeter.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkAction {
	private int id; // this will be primary key in database
	private LocalDateTime start;
	private LocalDateTime end;
	private Driver driver;

	public ParkAction() {
	}

	public ParkAction(LocalDateTime start, Driver driver) {
		this.start = start;
		this.driver = driver;
	}

	private BigDecimal calculateFee(ParkingCost parkingCost) {

		int hoursToPayFor = hoursToPayFor();

		/* calculating the cost */
		double fee = 0;

		fee += parkingCost.getFirstHourValue();
		hoursToPayFor -= 1;

		if (hoursToPayFor > 0) {
			fee += parkingCost.getSecondHOurValue();
			hoursToPayFor -= 1;
		}

		double lastHourCost = parkingCost.getSecondHOurValue();
		while (hoursToPayFor > 0) {
			lastHourCost *= parkingCost.getOverTwoHoursMultiplier();
			fee += lastHourCost;
			hoursToPayFor -= 1;
		}
		/* end of calculating the cost */

		BigDecimal finalFee = new BigDecimal(fee);
		finalFee = finalFee.setScale(2, RoundingMode.HALF_UP);

		return finalFee;
	}

	private int hoursToPayFor() {
		
		LocalDateTime endBoundary = (end!=null)?end:LocalDateTime.now();
		long months = start.until(endBoundary, ChronoUnit.MONTHS);
		long days = start.until(endBoundary, ChronoUnit.DAYS);
		long hours = start.until(endBoundary, ChronoUnit.HOURS);
		long minutes = start.until(endBoundary, ChronoUnit.MINUTES);
		
		int result = (int) (months*30*24 + days*24 + hours + ((minutes > 0)?1:0));
		
		return result;
	}

	public List<DriverCharge> calculateCharges() {
		List<DriverCharge> charges = new ArrayList<>();
		for (ParkingCost parkingCost : driver.getDriverType().getCosts()) {
			charges.add(new DriverCharge(parkingCost.getCurrency(), calculateFee(parkingCost)));
		}
		return charges;
	}

	public void updateProperties(ParkAction otherParkAction) {
		this.driver = otherParkAction.getDriver();
		this.start = otherParkAction.getStart();
		this.end = otherParkAction.getEnd();
		this.id = otherParkAction.getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
