package com.lkd.portal.xml.jackson.bc.services;

import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jackson.bc.mappers.LkdBcProduct2EntityMapper;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffers;
import com.shopizer.inventory.entity.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.entity.in.product.services.ProductImportSettings;
import com.shopizer.inventory.entity.in.product.services.ProductRequestEntityWriter;

public class LkBcXml2ShopizerEntityController {

	LkdGenericLogger logger = new LkdGenericLogger();

	private static String getImportJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = ProductImportSettings.getImportFileBaseDir() + "product-converter-" + idx + ".json";
		}
		return sF;
	}
	
	/**
	 * where to find csv
	 */
	private static String getRootDataDir() {
		int ii = 0;
		String sF = "";
		if (ii == 0) {

			sF = sF + "C://lkd//ht//apps_java8_in_action//app//src//";
			sF = sF + "shopizer-inventory-csv//src//main//resources";
		}
		return sF;
	}

	private static String getDebugJsonFileName() {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = getRootDataDir() + "//bc-data//oferta_test_2_dbg_reader.xml";
		}
		return sF;
	}

	private static String getImportJsonFileName() {
		int ii = 1;
		String sF = "";
		if (ii == 0) {
			sF = getRootDataDir() + "//bc-data//ofertaxml_short_2018_01_17_3.xml";
		}
		if (ii == 1) {
			sF = getRootDataDir() + "//bc-data//oferta_test_2.xml";
		}
		
		return sF;
	}

	public static void main(String[] args) {

		try {

			int ii = 0;

			if (ii == 0) {
				LkBcXml2ShopizerEntityController productsImport = new LkBcXml2ShopizerEntityController();
				productsImport.mainImport();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mainImport() {
		String sMethod = "mainImport";
		loggerDebugM(sMethod, "start");
		
		try {

			LkdBcXmlReaderService productsImport = new LkdBcXmlReaderService();
			String fn = getImportJsonFileName();
			LkdBcOffers deserializedData = productsImport.deserializeFromXML(fn);
			LkdBcProduct2EntityMapper mapperHandler =  new LkdBcProduct2EntityMapper();
			LkdBcXmlWriterService productsExport = new LkdBcXmlWriterService();
			String fnw = getDebugJsonFileName();
			productsExport.serializeToXML(deserializedData, fnw);
			
			ProductsRequestEntityData dest = new ProductsRequestEntityData();
			mapperHandler.getRequestProductsDataFromXml(deserializedData,dest);
			ProductRequestEntityWriter dataWriter =  new ProductRequestEntityWriter();
			
			String fcnw1 = getImportJsonFileName("cnv-1");
			dataWriter.writeRecord(dest, 0, fcnw1);
			loggerDebugM(sMethod, "end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			loggerExceptionM("", "", e);
		}

	}

	private String getDbgClassName() {
		return "LkBcXml2ShopizerEntityController::";
	}

	private void loggerDebug(String ttx) {
		String stx = getDbgClassName() + ttx;
		logger.debug(stx);
	}

	private void loggerDebugM(String sMethod, String ttx) {
		String stx = getDbgClassName() + ":" + sMethod + ":" + ttx;
		logger.debug(stx);
	}

	private void loggerExceptionM(String sMethod, String ttx, Exception ex) {
		String stx = getDbgClassName() + ":" + sMethod + ":" + ttx;
		logger.debug(stx);
		logger.exception(stx, ex);
	}

}
