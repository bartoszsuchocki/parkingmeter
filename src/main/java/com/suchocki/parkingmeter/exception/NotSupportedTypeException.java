package com.suchocki.parkingmeter.exception;

public class NotSupportedTypeException extends RuntimeException {

	private static final String MESSAGE = "This method does not support this type of record.";

	public NotSupportedTypeException() {
		super(MESSAGE);
	}
}
