package com.suchocki.parkingmeter.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.ParkActionDAO;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

@Service
public class ParkActionServiceImpl implements ParkActionService {

	@Autowired
	private ParkActionDAO parkActionDAO;

	@Override
	public void save(ParkAction parkAction) {
		parkActionDAO.save(parkAction);
	}

	@Override
	public ParkAction get(Integer id) {
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
	public ParkAction getDriverLastParkAction(Driver driver) {
		return parkActionDAO.getDriverLastParkAction(driver);
	}

	@Override
	public ParkAction finishParkAction(Driver driver) {
		ParkAction toFinish = parkActionDAO.getDriverLastParkAction(driver);
		if (toFinish == null) {
			return null; // in such situtation, i could also throw some exception, but, as it is a simple
							// app, i believe that null will also be ok (using this method I will check
							// if null returned)
		}
		toFinish.setEnd(new Date());
		save(toFinish);
		return toFinish;
	}
}
