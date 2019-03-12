package com.suchocki.parkingmeter.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	public List<DriverPayment> getAll() {
		return driverPaymentDAO.getAll();
	}

	@Override
	public List<DriverPayment> getByDay(Date date) {
		return driverPaymentDAO.getByDay(date);
	}

	@Override
	public List<DriverCharge> getPaymentsSumByday(Date date) {

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
		
		
		
//		Map<Currency, BigDecimal> paymentsSums = new LinkedHashMap<>(); // payments sums in different currencies
//		List<Currency> currencies = currencyService.getAll();
//
//		if (currencies == null) {
//			return null;
//		}
//
//		/* inserting '0 values' in order to add values to them later' */
//		for (Currency currency : currencies) {
//			paymentsSums.put(currency, new BigDecimal(0D).setScale(2, RoundingMode.HALF_UP));
//		}
//
//		/* end of inserting '0 values' */
//
//		/* summing */
//		List<DriverPayment> paymentsThisDay = getByDay(date);
//		for (DriverPayment payment : paymentsThisDay) {
//			if (payment.getCurrency() == null) {
//				continue;
//			}
//			BigDecimal fee = paymentsSums.get(payment.getCurrency());
//			paymentsSums.replace(payment.getCurrency(), fee.add(payment.getFee()).setScale(2, RoundingMode.HALF_UP));
//
//		}
//		/* end of summing */
//
//		List<DriverCharge> resultList = new ArrayList<>();
//
//		/* converting sum map to list */
//		for (Currency currency : paymentsSums.keySet()) {
//			resultList.add(new DriverCharge(currency, paymentsSums.get(currency)));
//		}
//		/* end of converting sum map to list */
//
//		return resultList;
	}
}
