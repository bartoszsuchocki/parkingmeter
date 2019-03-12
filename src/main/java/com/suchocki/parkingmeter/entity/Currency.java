package com.suchocki.parkingmeter.entity;

public class Currency {
	private String name;
	private String acronym; // this will be primary key in database;

	public Currency() {
	}

	public Currency(String name, String acronym) {
		this.name = name;
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public void updateProperties(Currency otherCurrency) {
		this.acronym = otherCurrency.getAcronym();
		this.name = otherCurrency.getName();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Currency)) {
			return false;
		}
		Currency otherCurrency = (Currency) object;
		if (otherCurrency.getAcronym() == null || otherCurrency.getName() == null) {
			return false;
		}
		return otherCurrency.getAcronym().equals(this.acronym) && otherCurrency.getName().equals(this.name);
	}
}
