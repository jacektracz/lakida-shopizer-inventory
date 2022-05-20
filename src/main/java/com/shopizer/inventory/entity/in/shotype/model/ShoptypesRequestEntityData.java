package com.shopizer.inventory.entity.in.shotype.model;

import java.util.ArrayList;
import java.util.List;

public class ShoptypesRequestEntityData {
	
	private List<ShoptypeRequestEntityData> manufacturerItems = new ArrayList<>();

	public List<ShoptypeRequestEntityData> getManufacturerItems() {
		return manufacturerItems;
	}

	public void setManufacturerItems(List<ShoptypeRequestEntityData> manufacturerItems) {
		this.manufacturerItems = manufacturerItems;
	}
}
