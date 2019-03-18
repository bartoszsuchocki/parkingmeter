package com.suchocki.parkingmeter.service;

import java.util.List;

import com.suchocki.parkingmeter.entity.Currency;

public interface CurrencyService extends Service<Currency, String> {
	public List<Currency> getAll();
}
