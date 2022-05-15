package com.shopizer.inventory.csv.in.product.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.shopizer.inventory.csv.in.product.mapper.ProductRequesEntity2MapMapper;
import com.shopizer.inventory.csv.in.product.mapper.ProductRequesCsv2MapMapper;
import com.shopizer.inventory.csv.in.product.mapper.ProductRequestCsv2EntityMapper;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestMapData;

public class ProductImportCsvReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportCsvReader.class);


	private static String getCsvFileName() {
		int ii = 1;
		String sF = "";
		if (ii == 0) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader2.csv";
		}
		if (ii == 1) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader3.md";
		}
		if (ii == 2) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader.csv";
		}
		if (ii == 3) {
			sF = "C://lkd//ht-shopizer//import//product-loader2.csv";
		}
		if (ii == 4) {
			sF = "C://lkd//ht-shopizer//import//product-loader3.md";
		}
		return sF;
	}


	private static String getDebugFileName() {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader3.json";
		}
		return sF;
	}


	public ProductsRequestMapData getMappedEntityV1() {
		String sMethod = "getMappedEntityV1";
		loggerDebugM(sMethod, "start");
		
		try {
			ProductsRequestMapData dataMapped = new ProductsRequestMapData();
			String fn = getCsvFileName();
			CSVParser parser = getParser(fn);
			new ProductRequesCsv2MapMapper().getRequestProductsDataFromCsv(parser, dataMapped);
			loggerDebugM(sMethod, "end");
			return dataMapped;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return null;
		}			
	}
	
	public ProductsRequestMapData getMappedEntityV2() {
		String sMethod = "getMappedEntityV2";
		loggerDebugM(sMethod, "start");
		
		try {
			ProductsRequestMapData dataMapped = new ProductsRequestMapData();	
			String fn = getCsvFileName();
			ProductsRequestEntityData  entityData = readProductsEntityDataFromCsv(fn);
			new ProductRequesEntity2MapMapper().getRequestProductsDataFromEntity(entityData, dataMapped);
			loggerDebugM(sMethod, "end");		
			return dataMapped;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return null;
		}					
	}
	
	public ProductsRequestEntityData readProductsEntityDataFromCsv(String fileName) throws Exception {

		String sMethod = "readProductsEntityDataFromCsv";
		loggerDebugM(sMethod, "start");
		try {
			CSVParser parser = getParser(fileName);

			ProductsRequestEntityData data = new ProductsRequestEntityData();
			ProductRequestCsv2EntityMapper mapper = new ProductRequestCsv2EntityMapper();
			mapper.getRequestProductsDataFromCsv(parser, data);
			String dbgFileName = getDebugFileName();
			mapper.debugRecord(data, 0, "");
			mapper.writeRecord(data, 0, dbgFileName);
			loggerDebugM(sMethod, "end");
			return data;

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return null;
		}

	}

	public ProductsRequestMapData readProductsMapFromCsv(String fileName) throws Exception {

		String sMethod = "readProductsFromCsv";
		loggerDebugM(sMethod, "start");

		CSVParser parser = getParser(fileName);

		ProductsRequestMapData data = new ProductsRequestMapData();
		new ProductRequesCsv2MapMapper().getRequestProductsDataFromCsv(parser, data);
		loggerDebugM(sMethod, "end");
		return data;

	}

	public CSVParser getParser(String fileName) {
		String sMethod = "getParser";
		loggerDebugM(sMethod, "start");
		try {
			CSVFormat format = CSVFormat.EXCEL.withHeader().withDelimiter(',');
			loggerDebugM(sMethod, "filename:" + fileName);
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader in = new BufferedReader(isr);

			@SuppressWarnings("resource")
			CSVParser parser = new CSVParser(in, format);
			if (parser == null) {
				loggerDebugM(sMethod, "parser-is-null-filename:" + fileName);
				return parser;
			}

			if (parser.getRecordNumber() == 0) {
				loggerDebugM(sMethod, "parser-record-number-is-0:" + fileName);
				return parser;
			}

			loggerDebugM(sMethod, "end:" + parser.getRecordNumber());
			return parser;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return null;
		}
	}


	private String getDbgClassName() {
		return "ProductFileManagerImpl::";
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
