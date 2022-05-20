package com.shopizer.inventory.entity.in.prodtype.model;

import java.util.ArrayList;
import java.util.List;

public class ProductTypesRequestEntityData {
	
	private List<ProductTypeRequestEntityData> manufacturerItems = new ArrayList<>();

	public List<ProductTypeRequestEntityData> getManufacturerItems() {
		return manufacturerItems;
	}

	public void setManufacturerItems(List<ProductTypeRequestEntityData> manufacturerItems) {
		this.manufacturerItems = manufacturerItems;
	}
}
