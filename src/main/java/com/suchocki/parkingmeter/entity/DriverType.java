package com.suchocki.parkingmeter.entity;

import java.math.BigDecimal;
import java.util.Map;

public class DriverType {
	private String typeName;
	private Map<Currency, BigDecimal> cost; // this will store cost in different currencies

	public DriverType() {;}
	public DriverType(String name) {
		this.typeName = name;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Map<Currency, BigDecimal> getCost() {
		return cost;
	}

	public void setCost(Map<Currency, BigDecimal> cost) {
		this.cost = cost;
	}

}
