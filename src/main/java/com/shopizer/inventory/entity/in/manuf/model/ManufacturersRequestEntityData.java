package com.shopizer.inventory.entity.in.manuf.model;

import java.util.ArrayList;
import java.util.List;

import com.shopizer.inventory.entity.in.manuf.model.ManufacturerRequestEntityData;

public class ManufacturersRequestEntityData {
	
	private List<ManufacturerRequestEntityData> manufacturerItems = new ArrayList<>();

	public List<ManufacturerRequestEntityData> getManufacturerItems() {
		return manufacturerItems;
	}

	public void setManufacturerItems(List<ManufacturerRequestEntityData> manufacturerItems) {
		this.manufacturerItems = manufacturerItems;
	}
}
