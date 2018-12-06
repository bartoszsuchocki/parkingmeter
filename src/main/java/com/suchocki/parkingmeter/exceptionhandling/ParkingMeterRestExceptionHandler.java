package com.suchocki.parkingmeter.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParkingMeterRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ParkingMeterErrorResponse> handleException(Exception e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ParkingMeterErrorResponse errorResponse = new ParkingMeterErrorResponse(e.getMessage(), status.value(),
				System.currentTimeMillis());
		return new ResponseEntity<ParkingMeterErrorResponse>(errorResponse, status);
	}

}
