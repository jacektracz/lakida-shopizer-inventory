package com.shopizer.inventory.csv.in.product.mapper;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.csv.in.product.services.ProductImport;

public class ProductRequestEntityMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRequestEntityMapper.class);

	public void getRequestProductsDataFromCsv(CSVParser parser,ProductsRequestEntityData dest) {
		
		String sMethod = "getRequestProductsDataFromCsv";
		loggerDebugM(sMethod, "start");

		int ii = 0;
		for (CSVRecord record : parser) {						
			loggerDebugM(sMethod, "start-record:" + ii);
			ProductRequestEntityData destItem = new ProductRequestEntityData();
			getRequestProductDataFromCsv(record, destItem);
			dest.getProductItems().add(destItem);
			loggerDebugM(sMethod, "end-record:" + ii);
			ii++;
		}
		loggerDebugM(sMethod, "end");		
	}
	
	private void getRequestProductDataFromCsv(CSVRecord parser,ProductRequestEntityData dest) {
		
		dest.setBarcode(recordGetString(parser,"barcode"));
		dest.setCategory(recordGetString(parser,"category"));
		dest.setDeal(recordGetString(parser,"deal"));
		dest.setDescriptionEn(recordGetString(parser,"description_en"));
		dest.setDimension(recordGetString(parser,"dimension"));
		dest.setDimensions(recordGetString(parser,"dimensions"));
		dest.setImageFile(recordGetString(parser,"image_file"));
		dest.setImageName(recordGetString(parser,"image_name"));
		dest.setImportStatus(recordGetString(parser,"import"));
		dest.setManufacturerCollection(recordGetString(parser,"collection"));
		dest.setNameEn(recordGetString(parser,"name_en"));
		dest.setPackageHeight(recordGetString(parser,"package_height"));
		dest.setPackageLength(recordGetString(parser,"package_length"));
		dest.setPackageWidth(recordGetString(parser,"package_width"));
		dest.setPosition(recordGetString(parser,"position"));
		dest.setPreOrder(recordGetString(parser,"pre-order"));
		dest.setPrice(recordGetString(parser,"price"));		
		dest.setQuantity(recordGetString(parser,"quantity"));		
		dest.setSku(recordGetString(parser,"sku"));		
		
	}
	
	private String getDbgClassName() {
		return "ProductRequestMapper::";
	}
	
	private boolean recordIsSetBoolean(CSVRecord record, String key) {
		String sMethod = "recordIsSetBoolean";
		loggerDebugM(sMethod, "start:" + key);

		if (!record.isSet(key)) {
			loggerDebugM(sMethod, "end:" + key + ":" + "false");
			return false;
		}
		loggerDebugM(sMethod, "end:" + key + ":" + "true");
		return true;
	}

	private String recordGetString(CSVRecord record, String key) {
		String sMethod = "recordGetString";
		loggerDebugM(sMethod, "start");

		if (!recordIsSetBoolean(record, key)) {
			return "";
		}
		String value = record.get(key);
		loggerDebugM(sMethod, "value:" + value);
		loggerDebugM(sMethod, "end");
		return value;
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
