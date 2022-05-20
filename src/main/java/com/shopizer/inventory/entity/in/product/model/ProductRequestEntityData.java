package com.shopizer.inventory.entity.in.product.model;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestEntityData {

	private String barcode;
	private String sku;
	private String imageName;
	private String imageFile;
	private String nameEn;
	private String descriptionEn;
	private String quantity;
	private String manufacturerCollection;
	private String category;
	private String packageHeight;
	private String packageWidth;
	private String packageLength;
	private String packageWeight;
	private String price;
	private String deal;
	private String preOrder;
	private String dimensions;
	private String position;
	private String importStatus;
	private String dimension;
	private String productType;
	private List<ProductRequestImageData> images;
	private List<ProductRequestOptionData> options;
	private ProductRequestOptionsGroupData sizeOptions;
	private ProductRequestOptionsGroupData colourOptions;
	public String getPackageHeight() {
		return packageHeight;
	}

	public void setPackageHeight(String packageHeight) {
		this.packageHeight = packageHeight;
	}

	public String getPackageWidth() {
		return packageWidth;
	}

	public void setPackageWidth(String packageWidth) {
		this.packageWidth = packageWidth;
	}

	public String getPackageLength() {
		return packageLength;
	}

	public void setPackageLength(String packageLength) {
		this.packageLength = packageLength;
	}

	public String getPackageWeight() {
		return packageWeight;
	}

	public void setPackageWeight(String packageWeight) {
		this.packageWeight = packageWeight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getPreOrder() {
		return preOrder;
	}

	public void setPreOrder(String preOrder) {
		this.preOrder = preOrder;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getManufacturerCollection() {
		return manufacturerCollection;
	}

	public void setManufacturerCollection(String manufacturerCollection) {
		this.manufacturerCollection = manufacturerCollection;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<ProductRequestImageData> getImages() {
		if (images == null) {
			images = new ArrayList<>();
		}
		return images;
	}

	public void setImages(List<ProductRequestImageData> images) {
		this.images = images;
	}

	public List<ProductRequestOptionData> getOptions() {
		return options;
	}

	public void setOptions(List<ProductRequestOptionData> options) {
		this.options = options;
	}

	public ProductRequestOptionsGroupData getSizeOptions() {
		return sizeOptions;
	}

	public void setSizeOptions(ProductRequestOptionsGroupData sizeOptions) {
		this.sizeOptions = sizeOptions;
	}

	public ProductRequestOptionsGroupData getColourOptions() {
		return colourOptions;
	}

	public void setColourOptions(ProductRequestOptionsGroupData colourOptions) {
		this.colourOptions = colourOptions;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
