package com.suchocki.parkingmeter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.CurrencyDAO;
import com.suchocki.parkingmeter.entity.Currency;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyDAO currencyDAO;

	@Override
	public void save(Currency currency) {
		currencyDAO.save(currency);
	}

	@Override
	public Optional<Currency> get(String id) {
		return currencyDAO.get(id);
	}

	public List<Currency> getAll() {
		return currencyDAO.getAll();
	}

	@Override
	public void update(Currency currency) {
		currencyDAO.update(currency);
	}

	@Override
	public void delete(String id) {
		currencyDAO.delete(id);
	}
}
