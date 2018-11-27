package com.suchocki.parkingmeter.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.DriverDAO;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private ParkActionService parkActionService;

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

	public boolean existsInDb(Driver driver) {
		if (driverDAO.get(driver.getLicensePlate()) == null) {
			return false;
		}
		return true;
	}
}
