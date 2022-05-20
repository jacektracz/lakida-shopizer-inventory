package com.shopizer.inventory.entity.in.product.model;

import java.util.ArrayList;
import java.util.List;

public class ProductsRequestEntityData {
	
	private List<ProductRequestEntityData> productItems = new ArrayList<>();

	public List<ProductRequestEntityData> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductRequestEntityData> productItems) {
		this.productItems = productItems;
	}
}
