package com.suchocki.parkingmeter.propertyeditor;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePropertyEditor extends PropertyEditorSupport {

	private DateTimeFormatter dateFormatter;

	public DatePropertyEditor() {
		dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LocalDate date = LocalDate.parse(text, dateFormatter);
		this.setValue(date);
	}

	@Override
	public String getAsText() {
		LocalDate date = (LocalDate) this.getValue();
		return date.toString();
	}
}
