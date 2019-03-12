package com.suchocki.parkingmeter.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.ParkActionDAO;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;
import com.suchocki.parkingmeter.exception.NoParkActionStartedException;

@Service
public class ParkActionServiceImpl implements ParkActionService {

	@Autowired
	private ParkActionDAO parkActionDAO;

	@Override
	public void save(ParkAction parkAction) {
		parkActionDAO.save(parkAction);
	}

	@Override
	public Optional<ParkAction> get(Integer id) {
		return parkActionDAO.get(id);
	}

	@Override
	public List<ParkAction> getAll() {
		return parkActionDAO.getAll();
	}

	@Override
	public void update(ParkAction parkAction) {
		parkActionDAO.update(parkAction);
	}

	@Override
	public void delete(Integer id) {
		parkActionDAO.delete(id);
	}

	@Override
	public Optional<ParkAction> getDriverLastParkAction(Driver driver) {
		return parkActionDAO.getDriverLastParkAction(driver);
	}

	@Override
	public Optional<ParkAction> finishParkAction(Driver driver) {
		return parkActionDAO.getDriverLastParkAction(driver);
	}
}
