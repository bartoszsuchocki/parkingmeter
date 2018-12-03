package com.suchocki.parkingmeter.entity;

import java.util.ArrayList;
import java.util.List;

public class DriverType {
	private String typeName; // typeName will be primary key in database

	private List<ParkingCost> costs; // costs will store parking costs in different currencies

	public DriverType() {
		;
	}

	public DriverType(String name) {
		this.typeName = name;
	}

	public void addCost(ParkingCost cost) {
		if (costs == null) {
			costs = new ArrayList<>();
		}
		costs.add(cost);
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<ParkingCost> getCosts() {
		return costs;
	}

	public void setCosts(List<ParkingCost> costs) {
		this.costs = costs;
	}

	public ParkingCost getCost(Currency currency) {
		for (ParkingCost cost : costs) {
			if (cost.getCurrency().equals(currency)) {
				return cost;
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DriverType)) {
			return false;
		}
		DriverType otherDriverType = (DriverType) object;
		return otherDriverType.getTypeName().equals(this.typeName);
	}

}
