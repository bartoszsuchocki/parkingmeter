package com.suchocki.parkingmeter.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public DriverPayment get(int id) {
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

		Map<Currency, BigDecimal> paymentsSums = new HashMap<>(); // payments sums in different currencies
		List<Currency> currencies = currencyService.getAll();

		/* inserting '0 values' in order to add values to them later' */
		for (Currency currency : currencies) {
			paymentsSums.put(currency, new BigDecimal(0D));
		}
		/* end of inserting '0 values' */

		/* summing */
		List<DriverPayment> paymentsThisDay = getByDay(date);
		for (DriverPayment payment : paymentsThisDay) {
			BigDecimal fee = paymentsSums.get(payment.getCurrency());
			fee = fee.add(payment.getFee());
		}
		/* end of summing */

		List<DriverCharge> resultList = new ArrayList<>();

		/* converting sum map to list */
		for (Currency currency : paymentsSums.keySet()) {
			resultList.add(new DriverCharge(currency, paymentsSums.get(currency)));
		}
		/* end of converting sum map to list */

		return resultList;
	}

}
