package com.shopizer.inventory.entity.in.shotype.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.manufacturer.PersistableManufacturer;
import com.shopizer.inventory.entity.in.shotype.model.ShoptypeRequestEntityData;
import com.shopizer.inventory.entity.in.shotype.model.ShoptypesRequestEntityData;

public class ShoptypeImportByEntityHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoptypeImportByEntityHandler.class);
	private static final String endPoint = "http://localhost:8080/api/v1/private/manufacturer?store=";

	private String IMAGE_EXT = "PNG";
	private static final String MERCHANT = "DEFAULT";
	private static boolean DRY_RUN = false;
	private final int MAX_COUNT = 100;

	/**
	 * where to find csv
	 */
	private static String getImportFileBaseDir() {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = sF + "C://lkd//ht//apps_java8_in_action//app//src//";
			sF = sF + "shopizer-inventory-csv//src//main//resources//";
			sF = sF + "data-import//manufacturer//manufacturer-json//";
			sF = sF + "";
			sF = sF + "";
		}
		return sF;
	}

	private static String getDebugJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = getImportFileBaseDir() + "manufacturer-loader-" + idx + ".json";
		}
		return sF;
	}

	private static String getImportJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = getImportFileBaseDir() + "product-loader-" + idx + ".json";
		}
		return sF;
	}

	private static String getImgBaseDir() {
		// String IMAGE_BASE_DIR =
		// "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//imgs//";
		String IMAGE_BASE_DIR = "C://lkd//ht-shopizer//imgs//";
		return IMAGE_BASE_DIR;
	}

	public static void main(String[] args) {

		try {

			int ii = 0;

			if (ii == 0) {
				ShoptypeImportByEntityHandler productsImport = new ShoptypeImportByEntityHandler();
				productsImport.mainImport();
			}

			if (ii == 2) {
				ShoptypeImportByEntityHandler productsImport = new ShoptypeImportByEntityHandler();
				productsImport.jsonReadDbgCheck();
			}
			if (ii == 3) {
				ShoptypeImportByEntityHandler productsImport = new ShoptypeImportByEntityHandler();
				productsImport.dataProducerExecutor();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dataProducerExecutor() {
		String sMethod = "dataProducerExecutor";
		loggerDebugM(sMethod, "start");
		try {
			ShoptypesRequestEntityData products = new ShoptypesRequestEntityData();
			ShoptypeRequestEntityProducer producer = new ShoptypeRequestEntityProducer();
			producer.createRecord(products);
			ShoptypeRequestEntityWriter writerHandler = new ShoptypeRequestEntityWriter();
			String fn = getDebugJsonFileName("pi-4");
			writerHandler.writeRecord(products, 0, fn);
			loggerDebugM(sMethod, "end");
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
	}

	public void mainImport() {
		String sMethod = "mainImport";
		loggerDebugM(sMethod, "start");
		try {
			String ibd = getImgBaseDir();
			ShoptypeImportByEntityHandler productsImport = new ShoptypeImportByEntityHandler();
			String fn = getDebugJsonFileName("ld-4");

			productsImport.importManufacturers(DRY_RUN, endPoint, MERCHANT, fn, ibd, IMAGE_EXT, 1);
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
	}

	public void jsonReadDbgCheck() {
		String sMethod = "jsonReadDbgCheck";
		loggerDebugM(sMethod, "start");

		try {
			String fn = getImportJsonFileName("ld-4");
			ShoptypeRequestEntityReader entityReader = new ShoptypeRequestEntityReader();
			ShoptypesRequestEntityData entities = entityReader.readEntityRecordFromJsonFile(fn);

			ShoptypeRequestEntityWriter writerHandler = new ShoptypeRequestEntityWriter();
			String json = writerHandler.getRecordAsJsonString(entities);
			loggerDebugM(sMethod, "json:" + json);
			String fnwrtite = getDebugJsonFileName("test-ld-4");
			writerHandler.writeRecord(entities, 0, fnwrtite);
			loggerDebugM(sMethod, "end");
			return;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return;
		}
	}

	private String getItemSelector() {
		return "-S1";
	}

	public void importManufacturers(boolean dryRun, String endpoint, String merchant, String fileName,
			String imgBaseDir, String imgExt, int importType) throws Exception {

		String sMethod = "importManufacturers";
		loggerDebugM(sMethod, "start");
		String fn = fileName;
		ShoptypeRequestEntityReader entityReader = new ShoptypeRequestEntityReader();
		ShoptypesRequestEntityData entityData = entityReader.readEntityRecordFromJsonFile(fn);

		if (entityData == null) {
			loggerDebugM(sMethod, "end");
			return;
		}
		List<ShoptypeRequestEntityData> products = entityData.getManufacturerItems();
		handleImportManufacturers(dryRun, endpoint, merchant, fileName, imgBaseDir, imgExt, entityData);

		loggerDebugM(sMethod, "end");
	}

	public void handleImportManufacturers(boolean dryRun, String endpoint, String merchant, String fileName,
			String imgBaseDir, String imgExt, ShoptypesRequestEntityData entitiesData) throws Exception {

		String sMethod = "handleImportManufacturers";
		loggerDebugM(sMethod, "start");

		int ii = 0;
		int count = 0;
		if (entitiesData == null) {
			loggerDebugM(sMethod, "end");
			return;
		}

		for (ShoptypeRequestEntityData entityData : entitiesData.getManufacturerItems()) {
			loggerDebugM(sMethod, "start-record:" + ii);
			boolean isOk = handleMappedRecord(entityData, ii, dryRun, endpoint, merchant, fileName, imgBaseDir, imgExt);
			if (isOk) {
				count++;
			}

			loggerDebugM(sMethod, "end-record:" + ii);
			ii++;
		}

		loggerDebug("------------------------------------");
		loggerDebug("Manufacturer import done " + count + " Dry Run [" + DRY_RUN + "]");
		loggerDebug("------------------------------------");
		loggerDebugM(sMethod, "end");
	}

	public boolean handleMappedRecord(ShoptypeRequestEntityData entityData, int ii, boolean dryRun, String endpoint,
			String merchant, String fileName, String imgBaseDir, String imgExt) {

		String sMethod = "handleMappedRecord";
		loggerDebugM(sMethod, "start");
		try {
			ShoptypeImportMapperByEntityService pis = new ShoptypeImportMapperByEntityService();

			loggerDebugM(sMethod, "start-record:" + ii);
			PersistableManufacturer product = new PersistableManufacturer();

			boolean recordOk = pis.handleRecord(entityData, product, ii, imgBaseDir, imgExt);
			if (!recordOk) {
				loggerDebugM(sMethod, "end-false-record:" + ii);
				return false;
			}

			// debugRecord(record, product, ii);

			ShoptypeImportByEntityController pic = new ShoptypeImportByEntityController();
			pic.sendRecord(entityData, product, ii, dryRun, endpoint + merchant);
			boolean isOkCount = handleMaxCount(entityData, product, ii);
			if (!isOkCount) {
				loggerDebugM(sMethod, "end-false-record:" + ii);
				return false;
			}
			loggerDebugM(sMethod, "end-record:" + ii);

			return true;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
	}

	public boolean handleMaxCount(ShoptypeRequestEntityData record, PersistableManufacturer product, int ii) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {
			loggerDebug("Created product in row " + ii + " Dry Run [" + DRY_RUN + "]");

			if (ii == MAX_COUNT) {
				loggerDebug("Reached MAX_COUNT [" + MAX_COUNT + "]");
				return false;
			}
			return true;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
	}

	private String getDbgClassName() {
		return "ManufacturerFileManagerImpl::";
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