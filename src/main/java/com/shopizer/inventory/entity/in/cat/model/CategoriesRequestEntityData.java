package com.shopizer.inventory.entity.in.cat.model;

import java.util.ArrayList;
import java.util.List;



public class CategoriesRequestEntityData {
	
	private List<CategoryRequestEntityData> manufacturerItems = new ArrayList<>();

	public List<CategoryRequestEntityData> getCategoryItems() {
		return manufacturerItems;
	}

	public void setCategoryItems(List<CategoryRequestEntityData> manufacturerItems) {
		this.manufacturerItems = manufacturerItems;
	}
}
