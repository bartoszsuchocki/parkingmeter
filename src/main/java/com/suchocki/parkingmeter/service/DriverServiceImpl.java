package com.suchocki.parkingmeter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.DriverDAO;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.ParkAction;
import com.suchocki.parkingmeter.exception.NoParkActionStartedException;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private ParkActionService parkActionService;

	@Autowired
	private DriverPaymentService driverPaymentService;

	@Autowired
	private DriverDAO driverDAO;

	@Override
	public ParkAction startParkingMeter(Driver driver) {
		if (!existsInDb(driver)) {
			driverDAO.save(driver);
		}
		ParkAction parkAction = new ParkAction(new Date(), driver);
		parkActionService.save(parkAction);
		return parkAction;
	}

	@Override
	public ParkAction stopParkingMeter(Driver driver) throws NoParkActionStartedException {
		Optional<ParkAction> finished = parkActionService.finishParkAction(driver);
		if (!finished.isPresent()) {
			throw new NoParkActionStartedException();
		}
		return finished.get();
	}

	@Override
	public boolean startedParkingMeter(String licensePlate) {
		Optional<Driver> driver = driverDAO.get(licensePlate);
		if (!driver.isPresent()) {
			return false;
		}
		Optional<ParkAction> lastParkAction = parkActionService.getDriverLastParkAction(driver.get());
		if (lastParkAction.isPresent() && lastParkAction.get().getEnd() == null) {
			return true;
		}
		return false;
	}

	@Override
	public List<DriverCharge> checkChargeForParkingTillNow(Driver driver) {
		Optional<ParkAction> lastParkAction = parkActionService.getDriverLastParkAction(driver);
		if (!lastParkAction.isPresent()) {
			return new ArrayList<DriverCharge>(); // empty list
		}
		return lastParkAction.get().calculateCharges();
	}

	@Override
	public List<DriverCharge> checkChargeForParkingTillNow(String licensePlate) {

		Driver driver = driverDAO.get(licensePlate).orElse(null);
		if (driver == null) {
			return null;
		}
		return checkChargeForParkingTillNow(driver);
	}

	@Override
	public DriverPayment pay(DriverPayment payment) {
		driverPaymentService.save(payment);
		return payment;
	}

	public boolean existsInDb(Driver driver) {
		if (driverDAO.get(driver.getLicensePlate()) == null) {
			return false;
		}
		return true;
	}
}
