package com.shopizer.inventory.entity.in.manuf.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.entity.in.manuf.model.ManufacturerRequestEntityData;
import com.shopizer.inventory.entity.in.manuf.model.ManufacturersRequestEntityData;


public class ManufacturerRequestEntityProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerRequestEntityProducer.class);
	
	
	
	public boolean createRecord(ManufacturersRequestEntityData products) {
		String sMethod = "createRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ManufacturerRequestEntityData product = new ManufacturerRequestEntityData();
			createRecordMainData(product);
			products.getManufacturerItems().add(product);
			loggerDebugM(sMethod, "end");
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean createRecordMainData(ManufacturerRequestEntityData product) {
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
