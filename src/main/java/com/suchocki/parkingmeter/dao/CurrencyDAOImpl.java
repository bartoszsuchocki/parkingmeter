package com.suchocki.parkingmeter.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.suchocki.parkingmeter.database.FakeDatabaseStub;
import com.suchocki.parkingmeter.entity.Currency;

@Repository
public class CurrencyDAOImpl implements CurrencyDAO {

	@Autowired
	private FakeDatabaseStub database;

	@Override
	public void save(Currency currency) {
		database.save(currency);
	}

	@Override
	public Optional<Currency> get(String id) {
		return database.get(id, Currency.class);
	}

	public List<Currency> getAll() {
		return database.getAll(Currency.class);
	}

	@Override
	public void update(Currency currency) {
		database.update(currency);
	}

	@Override
	public void delete(String id) {
		database.update(id);
	}

}
