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
import com.shopizer.inventory.entity.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.entity.in.product.model.ProductRequestImageData;
import com.shopizer.inventory.entity.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.entity.in.product.services.ProductImportByEntityHandler;

public class LkdBcProducts2EntitiesMapperController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LkdBcProducts2EntitiesMapperController.class);


	private String getDbgClassName() {
		return "LkdBcProducts2EntitiesMapperController::";
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
