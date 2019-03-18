package com.suchocki.parkingmeter.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suchocki.parkingmeter.dao.DriverPaymentDAO;
import com.suchocki.parkingmeter.entity.Currency;
import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;

@Service
public class DriverPaymentServiceImpl implements DriverPaymentService {

	@Autowired
	private DriverPaymentDAO driverPaymentDAO;

	@Autowired
	private CurrencyService currencyService;

	@Override
	public void save(DriverPayment driverPayment) {
		driverPaymentDAO.save(driverPayment);
	}

	@Override
	public Optional<DriverPayment> get(int id) {
		return driverPaymentDAO.get(id);
	}

	@Override
	public List<DriverPayment> getByDay(LocalDate date) {
		return driverPaymentDAO.getByDay(date);
	}

	@Override
	public List<DriverCharge> getPaymentsSumByday(LocalDate date) {

		List<DriverPayment> paymentsThisDay = getByDay(date);
		List<Currency> currencies = currencyService.getAll();
		List<DriverCharge> resultList = new ArrayList<>();

		for (Currency c : currencies) {
			Predicate<DriverPayment> properCurrencyPredicate = driverPayment -> driverPayment.getCurrency() != null
					&& driverPayment.getCurrency().equals(c);
			BigDecimal fee = paymentsThisDay.stream().filter(properCurrencyPredicate).map(DriverPayment::getFee)
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
			resultList.add(new DriverCharge(c, fee));
		}

		return resultList;
	}
}
