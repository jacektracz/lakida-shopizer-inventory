package com.shopizer.inventory.entity.in.cat.services;

import java.io.FileInputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.entity.in.cat.model.CategoriesRequestEntityData;



public class CategoryRequestEntityReader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRequestEntityReader.class);
	
	private String getDbgClassName() {
		return "CategoryRequestEntityReader::";
	}
	
	public CategoriesRequestEntityData readEntityRecordFromJsonFile(String fileName) {
		String sMethod = "readEntityRecordFromJsonFile";
		loggerDebugM(sMethod, "start");
		try {
			loggerDebugM(sMethod, "file-name:" + fileName);	
			ObjectMapper mapper = new ObjectMapper();
			CategoriesRequestEntityData mp = mapper.readValue(new FileInputStream(fileName),
					CategoriesRequestEntityData.class);
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
