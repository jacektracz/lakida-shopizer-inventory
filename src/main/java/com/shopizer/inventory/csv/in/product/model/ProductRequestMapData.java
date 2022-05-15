package com.shopizer.inventory.csv.in.product.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductRequestMapData {	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRequestMapData.class);
	private Map<String,String> values =  new HashMap<>();

	public Map<String,String> getValues() {
		return values;
	}

	public void setValues(Map<String,String> values) {
		this.values = values;
	}
	
	private String getDbgClassName() {
		return "ProductRequestMapData::";
	}
	
	public boolean recordIsSetBoolean( String key) {
		String sMethod = "recordIsSetBoolean";
		loggerDebugM(sMethod, "start:" + key);

		boolean retvalue = false;
		if(values.containsKey(key)) {
			retvalue = true;
		}
		loggerDebugM(sMethod, "end:" + key + ":" + "retval:" + retvalue);
		return retvalue;
	}

	public String recordGetString(String key,String defValue) {
		String sMethod = "recordGetString";
		loggerDebugM(sMethod, "start");
		String retvalue = defValue;
		if(values.containsKey(key)) {
			retvalue = values.get(key);
		}
		
		loggerDebugM(sMethod, "value:" + retvalue);
		loggerDebugM(sMethod, "end");
		return retvalue;
	}

	public String recordAddString( String key, String value) {
		String sMethod = "recordAddString";
		loggerDebugM(sMethod, "key:" + key);
		loggerDebugM(sMethod, "value:" + value);		
		if(!values.containsKey(key)) {
			values.put(key, value);
		}
		loggerDebugM(sMethod, "end");
		return value;
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
