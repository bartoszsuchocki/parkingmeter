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
				c.setName(currency.getName());
			}
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

}
