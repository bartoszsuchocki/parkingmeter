package com.suchocki.parkingmeter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Currency;

@Repository
public class CurrencyDAOImpl implements CurrencyDAO {

	@Override
	public void save(Currency currency) {
		if (get(currency.getAcronym()) == null) {
			FakeDatabaseStub.currencies.add(currency);
		} else {
			update(currency);
		}
	}

	@Override
	public Currency get(String id) {
		for (Currency c : FakeDatabaseStub.currencies) {
			if (c.getAcronym().equals(id)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public List<Currency> getAll() {
		return FakeDatabaseStub.currencies;
	}

	@Override
	public void update(Currency currency) {
		for (Currency c : FakeDatabaseStub.currencies) {
			if (c.getAcronym().equals(currency.getAcronym())) {
				c.updateProperties(currency);
			}
		}
	}

	@Override
	public void delete(String id) {
		int counter = 0;
		for (Currency currency : FakeDatabaseStub.currencies) {
			if (currency.getAcronym().equals(id)) {
				break;
			}
			counter++;
		}
		FakeDatabaseStub.currencies.remove(counter);
	}

}
