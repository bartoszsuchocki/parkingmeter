package com.suchocki.parkingmeter.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.suchocki.parkingmeter.entity.Currency;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.ParkAction;
import com.suchocki.parkingmeter.exception.NotMatchingTableException;
import com.suchocki.parkingmeter.exception.NotSupportedTypeException;

public final class FakeDatabaseStub {

	private List<ParkAction> parkActions = new ArrayList<>();
	private List<Driver> drivers = new ArrayList<>();
	private List<DriverPayment> driverPayments = new ArrayList<>();
	private List<Currency> currencies = new ArrayList<>();

	/* universal object methods */
	public void save(Object object) {
		if (object instanceof Currency) {
			saveCurrency((Currency) object);
		} else if (object instanceof Driver) {
			saveDriver((Driver) object);
		} else if (object instanceof DriverPayment) {
			saveDriverPayment((DriverPayment) object);
		} else if (object instanceof ParkAction) {
			saveParkAction((ParkAction) object);
		} else {
			throw new NotSupportedTypeException();
		}
	}

	public <T extends Object> Optional<T> get(String id, Class<T> type) {
		if (type == Currency.class) {
			return (Optional<T>) getCurrency(id);
		} else if (type == Driver.class) {
			return (Optional<T>) getDriver(id);
		}
		throw new NotMatchingTableException();
	}

	public <T extends Object> Optional<T> get(Integer id, Class<T> type) {
		if (type == DriverPayment.class) {
			return (Optional<T>) getDriverPayment(id);
		} else if (type == ParkAction.class) {
			return (Optional<T>) getParkAction(id);
		}
		throw new NotMatchingTableException();
	}

	public void update(Object object) {
		if (object instanceof Currency) {
			updateCurrency((Currency) object);
		} else if (object instanceof Driver) {
			updateDriver((Driver) object);
		} else if (object instanceof ParkAction) {
			updateParkAction((ParkAction) object);
		} else {
			throw new NotSupportedTypeException();
		}
	}

	public void delete(String id, Class<?> type) {
		if (type == Currency.class) {
			deleteCurrency(id);
		} else if (type == Driver.class) {
			deleteDriver(id);
		} else {
			throw new NotMatchingTableException();
		}
	}

	public void delete(Integer id, Class<?> type) {
		if (type == ParkAction.class) {
			deleteParkAction(id);
		} else if (type == DriverPayment.class) {
			throw new NotSupportedTypeException();
		} else {
			throw new NotMatchingTableException();
		}
	}

	public <T extends Object> List<T> getAll(Class<T> type) {
		if (type == Currency.class) {
			return (List<T>) currencies;
		}
		throw new NotSupportedTypeException();
	}

	public void clear(Class<?> type) {
		if (type == Currency.class) {
			currencies.clear();
		} else if (type == Driver.class) {
			drivers.clear();
		} else if (type == DriverPayment.class) {
			driverPayments.clear();
		} else if (type == ParkAction.class) {
			parkActions.clear();
		} else {
			throw new NotSupportedTypeException();
		}
	}

	/* currency management */
	private void saveCurrency(Currency currency) {
		if (getCurrency(currency.getAcronym()).isPresent()) {
			updateCurrency(currency);
		} else {
			currencies.add(currency);
		}
	}

	private Optional<Currency> getCurrency(String id) {
		return currencies.stream().filter(currency -> currency.getAcronym().equals(id)).findFirst();
	}

	private void updateCurrency(Currency currency) {
		Predicate<Currency> predicate = (currencyItem) -> (currencyItem.getAcronym().equals(currency.getAcronym()));
		Optional<Currency> currencyToUpdate = currencies.stream().filter(predicate).findFirst();
		currencyToUpdate.ifPresent(toUpdate -> toUpdate.updateProperties(currency));
	}

	private void deleteCurrency(String id) {
		currencies.removeIf(currency -> currency.getAcronym().equals(id));
	}

	/* driver management */

	private void saveDriver(Driver driver) {
		if (getDriver(driver.getLicensePlate()).isPresent()) {
			updateDriver(driver);
		} else {
			drivers.add(driver);
		}
	}

	private Optional<Driver> getDriver(String id) {
		return drivers.stream().filter(driver -> driver.getLicensePlate().equals(id)).findFirst();
	}

	private void updateDriver(Driver driver) {
		Predicate<Driver> predicate = d -> driver.getLicensePlate().equals(d.getLicensePlate());
		Optional<Driver> toUpdate = drivers.stream().filter(predicate).findFirst();
		toUpdate.ifPresent(d -> d.updateProperties(driver));
	}

	private void deleteDriver(String licensePlate) {
		drivers.removeIf(d -> d.getLicensePlate().equals(licensePlate));
	}

	/* DriverPayment management */
	private void saveDriverPayment(DriverPayment driverPayment) {
		synchronized (driverPayment) {
			driverPayment.setId(driverPayments.size() + 1);
		}
		driverPayments.add(driverPayment);
	}

	private Optional<DriverPayment> getDriverPayment(int id) {
		Predicate<DriverPayment> predicate = payment -> payment.getId() == id;
		return driverPayments.stream().filter(predicate).findFirst();
	}

	public List<DriverPayment> getDriverPaymentByDay(LocalDate date) { // this method could be replaced by more
																		// universal method which queries appropriate
																		// table by some conditions, but it is only a
																		// fake
																		// class, so it is not neccessary
		Predicate<DriverPayment> predicate = payment -> payment.getPayDate().toLocalDate().equals(date);
		return driverPayments.stream().filter(predicate).collect(Collectors.toList());
	}

	/* ParkAction management */

	private void saveParkAction(ParkAction parkAction) {
		if (getParkAction(parkAction.getId()).isPresent()) {
			updateParkAction(parkAction);
		} else {
			synchronized (parkAction) {
				parkAction.setId(parkActions.size() + 1);
				parkActions.add(parkAction);
			}
		}
	}

	private Optional<ParkAction> getParkAction(Integer id) {
		return parkActions.stream().filter(parkAction -> parkAction.getId() == id).findFirst();
	}

	private void updateParkAction(ParkAction parkAction) {
		Predicate<ParkAction> predicate = pAction -> pAction.getId() == parkAction.getId();
		Optional<ParkAction> toUpdate = parkActions.stream().filter(predicate).findFirst();
		toUpdate.ifPresent(pAction -> pAction.updateProperties(parkAction));
	}

	private void deleteParkAction(Integer id) {
		parkActions.removeIf(parkAction -> parkAction.getId() == id);
	}

	public Optional<ParkAction> getDriverLastParkAction(Driver driver) {// this method could be replaced by more
																		// universal method which queries appropriate
																		// table by some conditions, but it is only a
																		// fake
																		// class, so it is not neccessary
		Predicate<ParkAction> predicate = parkAction -> parkAction.getDriver().equals(driver);
		Comparator<ParkAction> byDateComparator = (ParkAction p1, ParkAction p2) -> {
			return (p1.getStart().isBefore(p2.getStart()) ? -1 : 1);
		};
		return parkActions.stream().filter(predicate).collect(Collectors.maxBy(byDateComparator));
	}

	public void printDB() {
		System.out.println("------------Database print--------------");
		System.out.println("CURRENCIES TABLE:");
		currencies.forEach(System.out::println);

		System.out.println("DRIVERS TABLE:");
		drivers.forEach(System.out::println);

		System.out.println("DRIVER PAYMENTS TABLE:");
		driverPayments.forEach(System.out::println);

		System.out.println("PARK ACTIONS TABLE:");
		parkActions.forEach(System.out::println);

		System.out.println("-----------End of database print---------");
	}
}
