package com.shopizer.inventory.entity.in.product.model;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestOptionsGroupData {
	
	private String optionType ; 
	private List<ProductRequestOptionData> productOptions = new ArrayList<>();

	public List<ProductRequestOptionData> getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(List<ProductRequestOptionData> productOptions) {
		this.productOptions = productOptions;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

}
