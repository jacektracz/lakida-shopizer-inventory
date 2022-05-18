package com.shopizer.inventory.csv.in.product.model;

public class ProductRequestOptionData {
	private String optionValue;
	private String optionQuantity;
	private String optionSku;
	private String optionPrice;
	
	private String optionCode;
	private String optionName;
	public String getOptionCode() {
		return optionCode;
	}
	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public String getOptionQuantity() {
		return optionQuantity;
	}
	public void setOptionQuantity(String optionQuantity) {
		this.optionQuantity = optionQuantity;
	}
	public String getOptionSku() {
		return optionSku;
	}
	public void setOptionSku(String optionSku) {
		this.optionSku = optionSku;
	}
	public String getOptionPrice() {
		return optionPrice;
	}
	public void setOptionPrice(String optionPrice) {
		this.optionPrice = optionPrice;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
}
