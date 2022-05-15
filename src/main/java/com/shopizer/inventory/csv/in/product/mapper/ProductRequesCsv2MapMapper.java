package com.shopizer.inventory.csv.in.product.mapper;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestMapData;
import com.shopizer.inventory.csv.in.product.services.ProductImport;

public class ProductRequesCsv2MapMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRequesCsv2MapMapper.class);

	public void getRequestProductsDataFromCsv(CSVParser parser,ProductsRequestMapData dest) {
		
		String sMethod = "getRequestProductsDataFromCsv";
		loggerDebugM(sMethod, "start");

		int ii = 0;
		for (CSVRecord record : parser) {						
			loggerDebugM(sMethod, "start-record:" + ii);
			ProductRequestMapData destItem = new ProductRequestMapData();
			getRequestProductDataFromCsv(record, destItem);
			dest.getProductItems().add(destItem);
			loggerDebugM(sMethod, "end-record:" + ii);
			ii++;
		}
		loggerDebugM(sMethod, "end");		
	}
	
	private void getRequestProductDataFromCsv(CSVRecord parser,ProductRequestMapData dest) {
		
		setDataFromCsvItem(dest,parser,"barcode");
		setDataFromCsvItem(dest,parser,"category");
		setDataFromCsvItem(dest,parser,"deal");
		setDataFromCsvItem(dest,parser,"description_en");
		setDataFromCsvItem(dest,parser,"dimension");
		setDataFromCsvItem(dest,parser,"dimensions");
		setDataFromCsvItem(dest,parser,"image_file");
		setDataFromCsvItem(dest,parser,"image_name");
		setDataFromCsvItem(dest,parser,"import");
		setDataFromCsvItem(dest,parser,"collection");
		setDataFromCsvItem(dest,parser,"name_en");
		setDataFromCsvItem(dest,parser,"package_height");
		setDataFromCsvItem(dest,parser,"package_length");
		setDataFromCsvItem(dest,parser,"package_width");
		setDataFromCsvItem(dest,parser,"position");
		setDataFromCsvItem(dest,parser,"pre-order");
		setDataFromCsvItem(dest,parser,"price");		
		setDataFromCsvItem(dest,parser,"quantity");		
		setDataFromCsvItem(dest,parser,"sku");				
	}
	
	private String getDbgClassName() {
		return "ProductRequestMapper::";
	}
	
	private boolean setDataFromCsvItem(ProductRequestMapData dest,CSVRecord record, String key) {
		String sMethod = "recordIsSetBoolean";
		loggerDebugM(sMethod, "start:" + key);
		String sx = recordGetString( record, key );
		dest.recordAddString(key, sx);
		loggerDebugM(sMethod, "end:" + key + ":" + "true");
		return true;
	}
	
	private void getRequestProductsMapDataFromEntity(CSVRecord parser,ProductRequestEntityData dest) {
		
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
