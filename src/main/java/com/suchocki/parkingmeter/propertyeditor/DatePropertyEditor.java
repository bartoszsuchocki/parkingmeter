package com.suchocki.parkingmeter.propertyeditor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePropertyEditor extends PropertyEditorSupport {

	SimpleDateFormat dateFormat;

	public DatePropertyEditor() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Date date = dateFormat.parse(text);
			this.setValue(date);
		} catch (ParseException e) {
			this.setValue(null);
		}
	}

	@Override
	public String getAsText() {
		Date date = (Date) this.getValue();
		return dateFormat.format(date);
	}
}
