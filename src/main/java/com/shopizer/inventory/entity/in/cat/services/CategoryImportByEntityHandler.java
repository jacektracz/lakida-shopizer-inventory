package com.shopizer.inventory.entity.in.cat.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.category.PersistableCategory;

import com.shopizer.inventory.entity.in.cat.model.CategoryRequestEntityData;
import com.shopizer.inventory.entity.in.cat.model.CategoriesRequestEntityData;

public class CategoryImportByEntityHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryImportByEntityHandler.class);
	private static final String endPoint = "http://localhost:8080/api/v1/private/category?store=";

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
			sF = sF + "data-import//category//category-json//";
			sF = sF + "";
			sF = sF + "";
		}
		return sF;
	}

	private static String getDebugJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = getImportFileBaseDir() + "category-loader-" + idx + ".json";
		}
		return sF;
	}

	private static String getImportJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = getImportFileBaseDir() + "category-loader-" + idx + ".json";
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
				CategoryImportByEntityHandler itemToSendsImport = new CategoryImportByEntityHandler();
				itemToSendsImport.mainImport();
			}

			if (ii == 2) {
				CategoryImportByEntityHandler itemToSendsImport = new CategoryImportByEntityHandler();
				itemToSendsImport.jsonReadDbgCheck();
			}
			if (ii == 3) {
				CategoryImportByEntityHandler itemToSendsImport = new CategoryImportByEntityHandler();
				itemToSendsImport.dataProducerExecutor();
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
			CategoriesRequestEntityData itemToSends = new CategoriesRequestEntityData();
			CategoryRequestEntityProducer producer = new CategoryRequestEntityProducer();
			producer.createRecord(itemToSends);
			CategoryRequestEntityWriter writerHandler = new CategoryRequestEntityWriter();
			String fn = getDebugJsonFileName("pi-4");
			writerHandler.writeRecord(itemToSends, 0, fn);
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
			CategoryImportByEntityHandler itemToSendsImport = new CategoryImportByEntityHandler();
			String fn = getDebugJsonFileName("ld-4");

			itemToSendsImport.importCategories(DRY_RUN, endPoint, MERCHANT, fn, ibd, IMAGE_EXT, 1);
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
	}

	public void jsonReadDbgCheck() {
		String sMethod = "jsonReadDbgCheck";
		loggerDebugM(sMethod, "start");

		try {
			String fn = getImportJsonFileName("ld-4");
			CategoryRequestEntityReader entityReader = new CategoryRequestEntityReader();
			CategoriesRequestEntityData entities = entityReader.readEntityRecordFromJsonFile(fn);

			CategoryRequestEntityWriter writerHandler = new CategoryRequestEntityWriter();
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
	
	public void importCategories(boolean dryRun, String endpoint, String merchant, String fileName, String imgBaseDir,
			String imgExt, int importType) throws Exception {

		String sMethod = "importCategories";
		loggerDebugM(sMethod, "start");
		String fn = fileName;
		CategoryRequestEntityReader entityReader = new CategoryRequestEntityReader();
		CategoriesRequestEntityData entityData = entityReader.readEntityRecordFromJsonFile(fn);
		
		
		if (entityData == null) {
			loggerDebugM(sMethod, "end");
			return;
		}
		List<CategoryRequestEntityData> itemToSends = entityData.getCategoryItems();
		handleImportCategories(dryRun, endpoint, merchant, fileName, imgBaseDir, imgExt, entityData);

		loggerDebugM(sMethod, "end");
	}
	

	public void handleImportCategories(boolean dryRun, String endpoint, String merchant, String fileName,
			String imgBaseDir, String imgExt, CategoriesRequestEntityData entitiesData) throws Exception {

		String sMethod = "handleImportCategories";
		loggerDebugM(sMethod, "start");

		int ii = 0;
		int count = 0;
		if (entitiesData == null) {
			loggerDebugM(sMethod, "end");
			return;
		}
		Map<String,PersistableCategory> categoryMap = new HashMap<String,PersistableCategory>();
		for (CategoryRequestEntityData entityData : entitiesData.getCategoryItems()) {
			loggerDebugM(sMethod, "start-record:" + ii);
			boolean isOk = handleMappedRecord(categoryMap,entityData, ii, dryRun, endpoint, merchant, fileName, imgBaseDir, imgExt);
			if (isOk) {
				count++;
			}

			loggerDebugM(sMethod, "end-record:" + ii);
			ii++;
		}

		loggerDebug("------------------------------------");
		loggerDebug("Category import done " + count + " Dry Run [" + DRY_RUN + "]");
		loggerDebug("------------------------------------");
		loggerDebugM(sMethod, "end");
	}

	public boolean handleMappedRecord(Map<String,PersistableCategory> categoryMap,CategoryRequestEntityData entityData, int ii, boolean dryRun, String endpoint,
			String merchant, String fileName, String imgBaseDir, String imgExt) {

		String sMethod = "handleMappedRecord";
		loggerDebugM(sMethod, "start");
		try {
			CategoryImportMapperByEntityService pis = new CategoryImportMapperByEntityService();

			loggerDebugM(sMethod, "start-record:" + ii);
			PersistableCategory itemToSend = new PersistableCategory();

			boolean recordOk = pis.handleRecord(categoryMap,entityData, itemToSend, ii, imgBaseDir, imgExt);
			if (!recordOk) {
				loggerDebugM(sMethod, "end-false-record:" + ii);
				return false;
			}

			// debugRecord(record, itemToSend, ii);

			CategoryImportByEntityController pic = new CategoryImportByEntityController();
			pic.sendRecord(entityData, itemToSend, ii, dryRun, endpoint + merchant);
			boolean isOkCount = handleMaxCount(entityData, itemToSend, ii);
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

	public boolean handleMaxCount(CategoryRequestEntityData record, PersistableCategory itemToSend, int ii) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {
			loggerDebug("Created itemToSend in row " + ii + " Dry Run [" + DRY_RUN + "]");

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
		return "CategoryFileManagerImpl::";
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
