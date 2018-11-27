package com.suchocki.parkingmeter.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suchocki.parkingmeter.entity.Currency;
import com.suchocki.parkingmeter.entity.Driver;
import com.suchocki.parkingmeter.entity.DriverType;
import com.suchocki.parkingmeter.entity.ParkAction;
import com.suchocki.parkingmeter.entity.ParkingCost;
import com.suchocki.parkingmeter.service.DriverService;

@RestController
@RequestMapping("/api")
public class ParkingRestController {

	@Autowired
	private DriverService driverService;

	@PostMapping("/start")
	public ParkAction start(@RequestBody Driver driver) {
		return driverService.startParkingMeter(driver);
	}

	@PostMapping("/stop")
	public ParkAction stop(@RequestBody Driver driver) {
		return driverService.stopParkingMeter(driver);
	}

	@GetMapping("/check/{licensePlate}")
	public boolean check(@PathVariable("licensePlate") String licensePlate) {
		return driverService.startedParkingMeter(licensePlate);
	}

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
