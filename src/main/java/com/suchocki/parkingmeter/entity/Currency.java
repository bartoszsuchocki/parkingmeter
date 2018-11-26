package com.suchocki.parkingmeter.entity;

public class Currency {
	private String name;
	private String acronym; // this would be id in the db;

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

}
