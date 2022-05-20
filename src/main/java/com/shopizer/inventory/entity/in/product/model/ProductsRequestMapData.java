package com.shopizer.inventory.entity.in.product.model;

import java.util.ArrayList;
import java.util.List;

public class ProductsRequestMapData {
	
	private List<ProductRequestMapData> productItems = new ArrayList<>();

	public List<ProductRequestMapData> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductRequestMapData> productItems) {
		this.productItems = productItems;
	}
}	
