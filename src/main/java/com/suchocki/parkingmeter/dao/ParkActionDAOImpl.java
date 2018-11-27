package com.suchocki.parkingmeter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

@Repository
public class ParkActionDAOImpl implements ParkActionDAO {

	@Override
	public void save(ParkAction parkAction) {
		for (ParkAction pAction : FakeDatabaseStub.parkActions) {
			if (pAction.getId() == parkAction.getId()) {
				update(parkAction);
			}
		}
		parkAction.setId(FakeDatabaseStub.parkActions.size() + 1);
		FakeDatabaseStub.parkActions.add(parkAction);
	}

	@Override
	public ParkAction get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkAction> getAll() {
		return FakeDatabaseStub.parkActions;
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
		ParkAction lastParkAction = null;
		for (ParkAction parkAction : FakeDatabaseStub.parkActions) {
			if (parkAction.getDriver().equals(driver)) {
				lastParkAction = parkAction;
			}
		}
		return lastParkAction;
	}

}
