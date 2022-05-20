package com.shopizer.inventory.entity.in.shotype.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.entity.in.shotype.model.ShoptypeRequestEntityData;
import com.shopizer.inventory.entity.in.shotype.model.ShoptypesRequestEntityData;


public class ShoptypeRequestEntityProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoptypeRequestEntityProducer.class);
	
	
	
	public boolean createRecord(ShoptypesRequestEntityData products) {
		String sMethod = "createRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ShoptypeRequestEntityData product = new ShoptypeRequestEntityData();
			createRecordMainData(product);
			products.getManufacturerItems().add(product);
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean createRecordMainData(ShoptypeRequestEntityData product) {
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