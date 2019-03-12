package com.suchocki.parkingmeter.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkAction {
	private int id; // this will be primary key in database
	private Date start;
	private Date end;
	private Driver driver;

	public ParkAction() {
	}

	public ParkAction(Date start, Driver driver) {
		this.start = start;
		this.driver = driver;
	}

	private BigDecimal calculateFee(ParkingCost parkingCost) {
		int hoursToPayFor;

		/* calculating number of hours to pay for */
		long differenceInMilis;
		if (end != null) {
			differenceInMilis = end.getTime() - start.getTime();
		} else {
			differenceInMilis = System.currentTimeMillis() - start.getTime();
		}
		hoursToPayFor = (int) Math.ceil(differenceInMilis / 3600000D);
		/* end of calculating number of hours to pay for */

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
