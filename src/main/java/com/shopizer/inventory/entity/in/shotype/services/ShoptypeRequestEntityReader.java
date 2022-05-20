package com.shopizer.inventory.entity.in.shotype.services;

import java.io.FileInputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.entity.in.shotype.model.ShoptypesRequestEntityData;



public class ShoptypeRequestEntityReader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoptypeRequestEntityReader.class);
	
	private String getDbgClassName() {
		return "ManufacturerRequestEntityReader::";
	}
	
	public ShoptypesRequestEntityData readEntityRecordFromJsonFile(String fileName) {
		String sMethod = "readEntityRecordFromJsonFile";
		loggerDebugM(sMethod, "start");
		try {
			loggerDebugM(sMethod, "file-name:" + fileName);	
			ObjectMapper mapper = new ObjectMapper();
			ShoptypesRequestEntityData mp = mapper.readValue(new FileInputStream(fileName),
					ShoptypesRequestEntityData.class);
			return mp;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return null;
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
