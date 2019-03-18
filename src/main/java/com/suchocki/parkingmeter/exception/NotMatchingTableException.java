package com.suchocki.parkingmeter.exception;

public class NotMatchingTableException extends RuntimeException {
	private static final String MESSAGE = "Type or Id of record matches no database table!";

	public NotMatchingTableException() {
		super(MESSAGE);
	}
}
