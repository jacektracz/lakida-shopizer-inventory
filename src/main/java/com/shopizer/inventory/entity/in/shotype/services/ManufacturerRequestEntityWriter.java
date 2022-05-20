package com.shopizer.inventory.entity.in.shotype.services;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.entity.in.shotype.model.ManufacturersRequestEntityData;


public class ManufacturerRequestEntityWriter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerRequestEntityWriter.class);
	
	private String getDbgClassName() {
		return "ManufacturerRequestEntityWriter::";
	}
	
	
	public boolean debugRecord(ManufacturersRequestEntityData product,int ii,String outputFile) {
		String sMethod = "debugRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = writer.writeValueAsString(product);
			
			loggerDebug("Line " + ii + " ********************");
			loggerDebug(json);
			
			
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean writeRecord(ManufacturersRequestEntityData product,int ii,String outputFile) {
		String sMethod = "writeRecord";
		loggerDebugM(sMethod, "start");
		
		String json = getRecordAsJsonString(product);
		if(json.isEmpty()) {
			loggerDebugM(sMethod, "end");
			return false;			
		}
		boolean otval = writeJsonStringToFile(json,ii,outputFile);
		loggerDebugM(sMethod, "end");
		return otval;
	}
	
	public boolean writeJsonStringToFile(String jsonStr,int ii,String outputFile) {
		String sMethod = "writeRecord";
		loggerDebugM(sMethod, "start");
				
		try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {			
			loggerDebugM(sMethod, "str-to-write:" + jsonStr);
            out.write(jsonStr);
            loggerDebugM(sMethod, "end");
            return true;
        } catch (Exception e) {
        	loggerExceptionM(sMethod, "end",e);
        	return false;
        }
		
	}

	public String getRecordAsJsonString(ManufacturersRequestEntityData product) {
		String sMethod = "getRecordAsJsonString";
		loggerDebugM(sMethod, "start");
		String json = "";
		try {
			
			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = writer.writeValueAsString(product);
			return json;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return "";
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
