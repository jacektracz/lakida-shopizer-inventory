package com.lkd.portal.xml.jackson.bc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jackson.bc.model.LkdBcAttr;
import com.lkd.portal.xml.jackson.bc.model.LkdBcDetails;
import com.lkd.portal.xml.jackson.bc.model.LkdBcImages;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffer;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffers;
import com.shopizer.inventory.entity.in.product.services.ProductImportByEntityHandler;

public class LkBcXmlWriterController {

	LkdGenericLogger logger = new LkdGenericLogger();

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
			sF = getRootDataDir() + "//bc-data//oferta_test_1_dbg.xml";
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
			sF = getRootDataDir() + "//bc-data//oferta_test_1.xml";
		}
		
		return sF;
	}

	public static void main(String[] args) {

		try {

			int ii = 0;

			if (ii == 0) {
				LkBcXmlWriterController productsImport = new LkBcXmlWriterController();
				productsImport.mainExport();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mainExport() {
		String sMethod = "mainExport";
		loggerDebugM(sMethod, "start");
		
		try {

			LkdBcOffers data = new LkdBcOffers();
			LkdBcOffer o1 = new LkdBcOffer();
			o1.setId("376");
			data.getOffer().add(o1);
			
			LkdBcOffer o2 = new LkdBcOffer();
			o2.setId("375");
			LkdBcImages im_2_1 = new LkdBcImages();
			im_2_1.getImage().add("img-1");
			im_2_1.getImage().add("img-2");
			im_2_1.getImage().add("img-3");
			o2.setImages(im_2_1);
			
			LkdBcAttr attr_2_0 = new LkdBcAttr();
			LkdBcDetails det_2_0 = new LkdBcDetails();
			det_2_0.setColor("black");
			attr_2_0.getDetails().add(det_2_0);
			o2.setAttr(attr_2_0);
			data.getOffer().add(o2);

			
			LkdBcXmlWriterService productsExport = new LkdBcXmlWriterService();
			String fnw = getDebugJsonFileName();
			productsExport.serializeToXML(data, fnw);
			loggerDebugM(sMethod, "end");
		} catch (Exception e) {
			loggerExceptionM("", "", e);
		}
	}

	private String getDbgClassName() {
		return "LkBcXmlReaderController::";
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
