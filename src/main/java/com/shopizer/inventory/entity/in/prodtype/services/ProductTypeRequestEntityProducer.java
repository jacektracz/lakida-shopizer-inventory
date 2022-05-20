package com.shopizer.inventory.entity.in.prodtype.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.entity.in.prodtype.model.ProductTypeRequestEntityData;
import com.shopizer.inventory.entity.in.prodtype.model.ProductTypesRequestEntityData;


public class ProductTypeRequestEntityProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeRequestEntityProducer.class);
	
	
	
	public boolean createRecord(ProductTypesRequestEntityData products) {
		String sMethod = "createRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ProductTypeRequestEntityData product = new ProductTypeRequestEntityData();
			createRecordMainData(product);
			products.getManufacturerItems().add(product);
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean createRecordMainData(ProductTypeRequestEntityData product) {
		String sMethod = "createRecordMainData";
		loggerDebugM(sMethod, "start");
		try {
			
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	private String getDbgClassName() {
		return "ManufacturerRequestEntityProducer::";
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
