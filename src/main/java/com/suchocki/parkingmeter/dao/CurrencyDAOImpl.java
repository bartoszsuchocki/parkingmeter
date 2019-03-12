package com.suchocki.parkingmeter.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Currency;

@Repository
public class CurrencyDAOImpl implements CurrencyDAO {

	@Override
	public void save(Currency currency) {
		if (get(currency.getAcronym()).isPresent()) {
			update(currency);
		} else {
			FakeDatabaseStub.currencies.add(currency);
		}
	}

	@Override
	public Optional<Currency> get(String id) {
		return FakeDatabaseStub.currencies.stream().filter(currency -> currency.getAcronym().equals(id)).findFirst();
	}

	@Override
	public List<Currency> getAll() {
		return FakeDatabaseStub.currencies;
	}

	@Override
	public void update(Currency currency) {
		Predicate<Currency> predicate = (currencyItem) -> (currencyItem.getAcronym().equals(currency.getAcronym()));
		Optional<Currency> currencyToUpdate = FakeDatabaseStub.currencies.stream().filter(predicate).findFirst();
		currencyToUpdate.ifPresent(toUpdate -> toUpdate.updateProperties(currency));
	}

	@Override
	public void delete(String id) {
		FakeDatabaseStub.currencies.removeIf(currency -> currency.getAcronym().equals(id));
	}

}
