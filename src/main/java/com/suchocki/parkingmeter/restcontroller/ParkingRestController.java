package com.suchocki.parkingmeter.restcontroller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverCharge;
import com.suchocki.parkingmeter.entity.DriverPayment;
import com.suchocki.parkingmeter.entity.ParkAction;
import com.suchocki.parkingmeter.propertyeditor.DatePropertyEditor;
import com.suchocki.parkingmeter.service.DriverService;
import com.suchocki.parkingmeter.service.ParkActionService;

@RestController
@RequestMapping("/api")
public class ParkingRestController {

	@Autowired
	private DriverService driverService;

	@Autowired
	private ParkActionService parkActionService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor());
	}

	@PostMapping("/start")
	public ParkAction start(@RequestBody Driver driver) {
		return driverService.startParkingMeter(driver);
	}

	@PostMapping("/stop")
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

	/*@PostMapping("/pay")
	public DriverPayment pay(@RequestBody Driver driver) {
		
		
		
		return null;
	}

	@PostMapping("/pay/{licensePlate}")
	public DriverPayment pay(@PathVariable("licensePlate") String licensePlate) {

		return null;
	}

	@GetMapping("/earning/{stringDate}") // date should be in this format: yyyy-mm-dd
	public BigDecimal getEarning(@PathVariable("stringDate") Date date) {

		return null;
	}*/

	// dorobic jeszcze /charge/licensePlate

//	@GetMapping("/get")
//	public Driver get() {
//		DriverType driverType = new DriverType("typ");
//
//		Currency currency = new Currency();
//		currency.setAcronym("pln");
//		currency.setName("zloty");
//		ParkingCost pc = new ParkingCost(currency);
//
//		driverType.addCost(pc);
//		Driver driver = new Driver();
//
//		driver.setDriverType(driverType);
//		driver.setLicensePlate("wf23223");
////		ParkAction parkAction = new ParkAction(new Date(), driver);
//		return driver;
//	}

}