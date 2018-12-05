package com.suchocki.parkingmeter.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.ParkActionDAO;
import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverCharge;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ParkAction object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ParkAction getDriverLastParkAction(Driver driver) {
		return parkActionDAO.getDriverLastParkAction(driver);
	}

	@Override
	public ParkAction finishParkAction(Driver driver) {
		ParkAction toFinish = parkActionDAO.getDriverLastParkAction(driver);
		if(toFinish == null) {
			return null; //walnac wyjatkiem
		}
		toFinish.setEnd(new Date());
		save(toFinish);
		return toFinish;
	}
	
	@Override
	public DriverCharge getChargeForADay(Date date) {
		for(ParkAction parkAction:FakeDatabaseStub.parkActions) {
//			parkAction
			//parkAction.getDriver().getDriverType().get
		}
		return null;
	}

}
