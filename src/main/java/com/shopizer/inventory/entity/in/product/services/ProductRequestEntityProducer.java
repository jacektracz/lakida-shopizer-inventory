package com.shopizer.inventory.entity.in.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestImageData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestOptionData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestOptionsGroupData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;

public class ProductRequestEntityProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRequestEntityProducer.class);
	
	private String getDbgClassName() {
		return "ProductRequestEntityProducer::";
	}
	
	
	public boolean createRecord(ProductsRequestEntityData products) {
		String sMethod = "createRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ProductRequestEntityData product = new ProductRequestEntityData();
			createRecordMainData(product);
			createRecordImages(product);
			createRecordOptionsSize(product);
			createRecordOptionsColours(product);
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean createRecordOptionsSize(ProductRequestEntityData product) {
		String sMethod = "createRecordOptionsSize";
		loggerDebugM(sMethod, "start");
		try {
			
			ProductRequestOptionData opt1 = new ProductRequestOptionData();
			opt1.setOptionCode("SIZE");
			opt1.setOptionName("XS");
			opt1.setOptionValue("XS");
			opt1.setOptionQuantity("10");
			opt1.setOptionSku("S7");
			opt1.setOptionPrice("22.1");
			
			ProductRequestOptionData opt2 = new ProductRequestOptionData();
			opt2.setOptionCode("SIZE");
			opt2.setOptionName("XL");
			opt2.setOptionValue("XL");
			opt2.setOptionQuantity("10");
			opt2.setOptionSku("S7");
			opt2.setOptionPrice("22.1");
			ProductRequestOptionsGroupData optSize = new ProductRequestOptionsGroupData();
			optSize.getProductOptions().add(opt2);
			optSize.getProductOptions().add(opt1);
			optSize.setOptionType("SIZE");

			product.setSizeOptions(optSize);
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	public boolean createRecordOptionsColours(ProductRequestEntityData product) {
		String sMethod = "createRecordOptionsSize";
		loggerDebugM(sMethod, "start");
		try {
			
			ProductRequestOptionData opt1 = new ProductRequestOptionData();
			opt1.setOptionCode("COLOUR");
			opt1.setOptionName("Black");
			opt1.setOptionValue("B");
			opt1.setOptionQuantity("10");
			opt1.setOptionSku("S7");
			opt1.setOptionPrice("22.1");
			
			ProductRequestOptionData opt2 = new ProductRequestOptionData();
			opt2.setOptionCode("COLOUR");
			opt2.setOptionName("Red");
			opt2.setOptionValue("RD");
			opt2.setOptionQuantity("10");
			opt2.setOptionSku("S7");
			opt2.setOptionPrice("22.1");
			ProductRequestOptionsGroupData optGroup = new ProductRequestOptionsGroupData();
			optGroup.getProductOptions().add(opt2);
			optGroup.getProductOptions().add(opt1);
			optGroup.setOptionType("COLOUR");

			product.setColourOptions(optGroup);
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean createRecordImages(ProductRequestEntityData product) {
		String sMethod = "createRecordImages";
		loggerDebugM(sMethod, "start");
		try {
			
			ProductRequestImageData img1 = new ProductRequestImageData();
			img1.setImageFile("img_1");
			img1.setImageName("img_1");
			product.getImages().add(img1);
			
			ProductRequestImageData img2 = new ProductRequestImageData();
			img1.setImageFile("img_2");
			img1.setImageName("img_2");
			product.getImages().add(img2);
			
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean createRecordMainData(ProductRequestEntityData product) {
		String sMethod = "createRecordMainData";
		loggerDebugM(sMethod, "start");
		try {
			
			product.setBarcode("B1");
			product.setSku("B1");
			product.setPreOrder("p1");
			product.setCategory("cat-1");
			product.setDeal("cat-1");
			product.setDimension("cat-1");
			product.setDimensions("cat-1");
			product.setDescriptionEn("cat-1");
			product.setImageFile("img_2");
			product.setImageName("img_2");
			product.setImportStatus("cat-1");
			product.setCategory("kobieta");
			product.setManufacturerCollection("estrada");
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}	
	
	private void loggerDebug(String ttx) {
		String stx = getDbgClassName() + ttx;
		LOGGER.debug(stx);
	}

	private void loggerDebugM(String sMethod, String ttx) {
		String stx = getDbgClassName() + ":" + sMethod + ":" + ttx;
		LOGGER.debug(stx);
	}

	private void loggerExceptionM(String sMethod, String ttx, Exception ex) {
		String stx = getDbgClassName() + ":" + sMethod + ":" + ttx;
		LOGGER.debug(stx);
		LOGGER.error(ex.getMessage());
	}	
}
