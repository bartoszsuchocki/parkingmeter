package com.suchocki.parkingmeter.dao;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.ParkAction;

@Repository
public class ParkActionDAOImpl implements ParkActionDAO {

	@Override
	public void save(ParkAction parkAction) {
		if (get(parkAction.getId()).isPresent()) {
			update(parkAction);
		} else {
			parkAction.setId(FakeDatabaseStub.parkActions.size() + 1);
			FakeDatabaseStub.parkActions.add(parkAction);
		}
	}

	@Override
	public Optional<ParkAction> get(Integer id) {
		return FakeDatabaseStub.parkActions.stream().filter(parkAction -> parkAction.getId() == id).findFirst();
	}

	@Override
	public List<ParkAction> getAll() {
		return FakeDatabaseStub.parkActions;
	}

	@Override
	public void update(ParkAction parkAction) {
		Predicate<ParkAction> predicate = pAction -> pAction.getId() == parkAction.getId();
		Optional<ParkAction> toUpdate = FakeDatabaseStub.parkActions.stream().filter(predicate).findFirst();
		toUpdate.ifPresent(pAction -> pAction.updateProperties(parkAction));
	}

	@Override
	public void delete(Integer id) {
		FakeDatabaseStub.parkActions.removeIf(parkAction -> parkAction.getId() == id);
	}

	@Override
	public Optional<ParkAction> getDriverLastParkAction(Driver driver) {
		Predicate<ParkAction> predicate = parkAction -> parkAction.getDriver().equals(driver);
		Comparator<ParkAction> byDateComparator = (ParkAction p1, ParkAction p2) -> {
			return (p1.getStart().isBefore(p2.getStart()) ? -1 : 1);
		};
		return FakeDatabaseStub.parkActions.stream().filter(predicate).collect(Collectors.maxBy(byDateComparator));
	}

}
