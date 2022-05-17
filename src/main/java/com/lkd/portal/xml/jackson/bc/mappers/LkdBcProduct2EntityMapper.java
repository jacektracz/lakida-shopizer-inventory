package com.lkd.portal.xml.jackson.bc.mappers;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkd.portal.xml.jackson.bc.model.LkdBcOffer;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffers;
import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestImageData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.csv.in.product.services.ProductImport;

public class LkdBcProduct2EntityMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LkdBcProduct2EntityMapper.class);

	public void getRequestProductsDataFromXml(LkdBcOffers parser,ProductsRequestEntityData dest) {
		
		String sMethod = "getRequestProductsDataFromXml";
		loggerDebugM(sMethod, "start");

		int ii = 0;
		for (LkdBcOffer record : parser.getOffer()) {						
			loggerDebugM(sMethod, "start-record:" + ii);
			ProductRequestEntityData destItem = new ProductRequestEntityData();
			getRequestProductDataFromXml(record, destItem);
			dest.getProductItems().add(destItem);
			loggerDebugM(sMethod, "end-record:" + ii);
			ii++;
		}
		loggerDebugM(sMethod, "end");		
	}
	
	private void getRequestProductDataFromXml(LkdBcOffer parser,ProductRequestEntityData dest) {
		String sMethod = "getRequestProductDataFromXml";
		loggerDebugM(sMethod, "start");
		
		dest.setBarcode(parser.getId());
		dest.setCategory(parser.getCategory());
		dest.setDeal(parser.getId());
		dest.setDescriptionEn(parser.getDescription());
		dest.setDimension("1");
		dest.setDimensions("2");
		
		int ii = 0;
		for ( String imgPath : parser.getImages().getImage()){
			if(ii == 0) {
				dest.setImageFile(imgPath);
				dest.setImageName(imgPath);
			}		
			ProductRequestImageData imgItem = new ProductRequestImageData();
			imgItem.setImageFile(imgPath);
			imgItem.setImageName(imgPath);
			dest.getImages().add(imgItem);
		}
		
		dest.setImportStatus("1");
		dest.setManufacturerCollection(parser.getCategory());
		dest.setNameEn(parser.getName());
		dest.setPackageHeight("1");
		dest.setPackageLength("1");
		dest.setPackageWidth("1");
		dest.setPosition("1");
		dest.setPreOrder("1");
		dest.setPrice(parser.getBruttosuggest());		
		dest.setQuantity("10");		
		dest.setSku(parser.getId());		
		loggerDebugM(sMethod, "start");
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
