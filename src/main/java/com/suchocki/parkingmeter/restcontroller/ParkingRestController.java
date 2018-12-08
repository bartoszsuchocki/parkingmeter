package com.suchocki.parkingmeter.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.ParkAction;
import com.suchocki.parkingmeter.propertyeditor.DatePropertyEditor;
import com.suchocki.parkingmeter.service.DriverPaymentService;
import com.suchocki.parkingmeter.service.DriverService;

@RestController
@RequestMapping("/api")
public class ParkingRestController {

	@Autowired
	private DriverService driverService;

	@Autowired
	private DriverPaymentService driverPaymentService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor());
	}

	@PostMapping("/start")
	public ParkAction start(@RequestBody Driver driver) {
		return driverService.startParkingMeter(driver);
	}

	@PutMapping("/stop")
	public List<DriverCharge> stop(@RequestBody Driver driver) {

		ParkAction finishedParkAction = driverService.stopParkingMeter(driver);
		if (finishedParkAction == null) {
			return null; // zastanowić się nad tym!
		}

		return finishedParkAction.calculateCharges();
	}

	@GetMapping("/check/{licensePlate}")
	public boolean check(@PathVariable("licensePlate") String licensePlate) {
		return driverService.startedParkingMeter(licensePlate);
	}

	@GetMapping("/charge")
	public List<DriverCharge> feeIfStoppedNow(@RequestBody Driver driver) {
		return driverService.checkChargeForParkingTillNow(driver);
	}

	@GetMapping("/charge/{licensePlate}")
	public List<DriverCharge> feeIfStoppedNow(@PathVariable("licensePlate") String licensePlate) {
		return driverService.checkChargeForParkingTillNow(licensePlate);
	}

	@PostMapping("/pay")
	public DriverPayment pay(@RequestBody DriverPayment payment) {
		return driverService.pay(payment);
	}

	@GetMapping("/earnings/{stringDate}") // date should be in this format: yyyy-mm-dd
	public List<DriverCharge> getEarning(@PathVariable("stringDate") Date date) {
		return driverPaymentService.getPaymentsSumByday(date);
	}

}
