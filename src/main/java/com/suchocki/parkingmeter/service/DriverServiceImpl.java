package com.suchocki.parkingmeter.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.DriverDAO;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.ParkAction;

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
	public ParkAction stopParkingMeter(Driver driver) {
		return parkActionService.finishParkAction(driver);
	}

	@Override
	public boolean startedParkingMeter(String licensePlate) {
		Driver driver = driverDAO.get(licensePlate);
		if (driver == null) {
			return false;
		}
		ParkAction lastParkAction = parkActionService.getDriverLastParkAction(driver);
		if (lastParkAction != null && lastParkAction.getEnd() == null) {
			return true;
		}
		return false;
	}

	@Override
	public List<DriverCharge> checkChargeForParkingTillNow(Driver driver) {
		ParkAction lastParkAction = parkActionService.getDriverLastParkAction(driver);
		if (lastParkAction == null) {
			return null;
		}
		return lastParkAction.calculateCharges();
	}

	@Override
	public List<DriverCharge> checkChargeForParkingTillNow(String licensePlate) {

		Driver driver = driverDAO.get(licensePlate);
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
