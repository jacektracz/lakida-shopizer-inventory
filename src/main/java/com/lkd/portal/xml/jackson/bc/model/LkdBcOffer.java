package com.lkd.portal.xml.jackson.bc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("offers")
public  class LkdBcOffer {

	@JsonProperty(required = false)
	protected String id;
	
	@JsonProperty(required = false)
	protected String name;
	
	@JsonProperty(required = false)
	protected String category;
	
	@JsonProperty(required = false)
	protected String categoryPath;
	
	@JsonProperty(required = false)
	protected String erpcode;
	
	@JsonProperty(required = false)
	protected String erpean;
	
	@JsonProperty(required = false)
	protected String brutto;
	
	@JsonProperty(required = false)
	protected String bruttosuggest;

	@JsonProperty(required = false)
	protected String url;
	
	@JsonProperty(required = false)
	protected LkdBcImages images;
	
	@JsonProperty(required = false)
	protected String manufacturer;
	
	@JsonProperty(required = false)
	protected String description;
	
	@JsonProperty(required = false)
	protected LkdBcAttr attr;
	
	@JsonProperty(required = false)
	protected LkdBcConnected connected;

	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String value) {
		this.category = value;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String value) {
		this.categoryPath = value;
	}

	public String getErpcode() {
		return erpcode;
	}

	public void setErpcode(String value) {
		this.erpcode = value;
	}

	public String getErpean() {
		return erpean;
	}

	public void setErpean(String value) {
		this.erpean = value;
	}

	public String getBrutto() {
		return brutto;
	}

	public void setBrutto(String value) {
		this.brutto = value;
	}

	
	public String getBruttosuggest() {
		return bruttosuggest;
	}

	
	public void setBruttosuggest(String value) {
		this.bruttosuggest = value;
	}

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String value) {
		this.url = value;
	}

	
	public LkdBcImages getImages() {
		return images;
	}

	
	public void setImages(LkdBcImages value) {
		this.images = value;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String value) {
		this.manufacturer = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public LkdBcAttr getAttr() {
		return attr;
	}

	public void setAttr(LkdBcAttr value) {
		this.attr = value;
	}

	public LkdBcConnected getConnected() {
		return connected;
	}

	public void setConnected(LkdBcConnected value) {
		this.connected = value;
	}




}
