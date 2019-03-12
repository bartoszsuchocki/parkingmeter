package com.suchocki.parkingmeter.exception;

public class NoParkActionStartedException extends Exception{
	
	private final static String MESSAGE = "No started park action found!";
	
	public NoParkActionStartedException() {
		super(MESSAGE);
	}
	
}
