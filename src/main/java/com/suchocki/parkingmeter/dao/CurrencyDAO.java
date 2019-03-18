package com.suchocki.parkingmeter.dao;

import java.util.List;

import com.suchocki.parkingmeter.entity.Currency;

public interface CurrencyDAO extends DAO<Currency, String> {
	public List<Currency> getAll();
}
