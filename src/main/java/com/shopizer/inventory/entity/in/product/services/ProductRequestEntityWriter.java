package com.shopizer.inventory.entity.in.product.services;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;

public class ProductRequestEntityWriter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRequestEntityWriter.class);
	
	private String getDbgClassName() {
		return "ProductRequestEntityWriter::";
	}
	
	
	public boolean debugRecord(ProductsRequestEntityData product,int ii,String outputFile) {
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
	public boolean writeRecord(ProductsRequestEntityData product,int ii,String outputFile) {
		String sMethod = "debugRecord";
		loggerDebugM(sMethod, "start");
		String json = "";
		try {
			
			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = writer.writeValueAsString(product);			
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
		
		try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
            out.write(json.toString());
            return true;
        } catch (Exception e) {
        	loggerExceptionM(sMethod, "end",e);
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
