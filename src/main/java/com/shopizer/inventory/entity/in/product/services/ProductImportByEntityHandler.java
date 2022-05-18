package com.shopizer.inventory.entity.in.product.services;

import java.io.FileInputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.shopizer.inventory.csv.in.product.mapper.ProductRequesEntity2MapMapper;
import com.shopizer.inventory.csv.in.product.mapper.ProductRequestCsv2EntityMapper;
import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestMapData;
import com.shopizer.inventory.map.in.product.services.ProductImportImageByMapService;

public class ProductImportByEntityHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportByEntityHandler.class);
	private static final String endPoint = "http://localhost:8080/api/v1/private/product?store=";
	
	private String IMAGE_EXT = "PNG";
	private static final String MERCHANT = "DEFAULT";
	private static boolean DRY_RUN = false;
	private final int MAX_COUNT = 100;

	/**
	 * where to find csv
	 */
	
	private static String getDebugJsonFileName() {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader3.json";
		}
		return sF;
	}

	private static String getImportJsonFileName() {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader4.json";
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
				ProductImportByEntityHandler productsImport = new ProductImportByEntityHandler();
				productsImport.mainImport();
			}

			if (ii == 1) {
				ProductImportByEntityHandler productsImport = new ProductImportByEntityHandler();
				productsImport.imagesDbgCheck();
			}

			if (ii == 2) {
				ProductImportByEntityHandler productsImport = new ProductImportByEntityHandler();
				productsImport.jsonReadDbgCheck();
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
			String ibd = getImgBaseDir();
			ProductImportByEntityHandler productsImport = new ProductImportByEntityHandler();
			String fn = "";
			productsImport.importProducts(DRY_RUN, endPoint, MERCHANT, fn, ibd, IMAGE_EXT, 1);
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
	}
	
	public void jsonReadDbgCheck() {
		String sMethod = "jsonReadDbgCheck";
		loggerDebugM(sMethod, "start");

		try {
			String fn = getImportJsonFileName();
			ProductsRequestEntityData entities = readEntityRecordFromJsonFile(fn);
			ProductsRequestMapData maps = new ProductsRequestMapData();
			new ProductRequesEntity2MapMapper().getRequestProductsDataFromEntity(entities, maps);
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
	}

	public void imagesDbgCheck() {
		String sMethod = "imagesDbgCheck";
		loggerDebugM(sMethod, "start");
		
		try {
			ProductImportImageByMapService productsImport = new ProductImportImageByMapService();
			String ibd = getImgBaseDir();
			productsImport.handleCheckImages(ibd, "img_2", IMAGE_EXT);
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
	}

	public void importProducts(boolean dryRun, String endpoint, String merchant, String fileName, String imgBaseDir,
			String imgExt, int importType) throws Exception {

		String sMethod = "importProducts";
		loggerDebugM(sMethod, "start");
		String fn = getImportJsonFileName();
		
		ProductsRequestEntityData entityData = readEntityRecordFromJsonFile(fn);
		
		if (entityData == null) {
			loggerDebugM(sMethod, "end");
			return;
		}		
		handleImportProducts(dryRun, endpoint, merchant, 
				fileName, imgBaseDir, 
				imgExt, entityData);

		loggerDebugM(sMethod, "end");
	}

	public void handleImportProducts(
			boolean dryRun, 
			String endpoint, 
			String merchant, 
			String fileName,
			String imgBaseDir, 
			String imgExt, 			
			ProductsRequestEntityData entitiesData) throws Exception {

		String sMethod = "handleImportProducts";
		loggerDebugM(sMethod, "start");

		int ii = 0;
		int count = 0;
		if (entitiesData == null) {
			loggerDebugM(sMethod, "end");
			return;
		}

		for (ProductRequestEntityData entityData : entitiesData.getProductItems()) {
			loggerDebugM(sMethod, "start-record:" + ii);
			boolean isOk = handleMappedRecord(entityData, ii, dryRun, endpoint, merchant, fileName, imgBaseDir, imgExt);
			if (isOk) {
				count++;
			}

			loggerDebugM(sMethod, "end-record:" + ii);
			ii++;
		}

		loggerDebug("------------------------------------");
		loggerDebug("Product import done " + count + " Dry Run [" + DRY_RUN + "]");
		loggerDebug("------------------------------------");
		loggerDebugM(sMethod, "end");
	}


	
	public boolean handleMappedRecord(ProductRequestEntityData entityData, int ii, boolean dryRun, String endpoint,
			String merchant, String fileName, String imgBaseDir, String imgExt) {

		String sMethod = "handleMappedRecord";
		loggerDebugM(sMethod, "start");
		try {
			ProductImportManagerByEntityService pis = new ProductImportManagerByEntityService();
			ProductImportByEntityController pic = new ProductImportByEntityController();

			loggerDebugM(sMethod, "start-record:" + ii);
			PersistableProduct product = new PersistableProduct();

			boolean recordOk = pis.handleRecord(entityData, product, ii, imgBaseDir, imgExt);
			if (!recordOk) {
				loggerDebugM(sMethod, "end-false-record:" + ii);
				return false;
			}

			//debugRecord(record, product, ii);

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

	public boolean handleMaxCount(ProductRequestEntityData record, PersistableProduct product, int ii) {
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

	public boolean debugRecord(ProductRequestMapData record, PersistableProduct product, int ii) {
		String sMethod = "debugRecord";
		loggerDebugM(sMethod, "start");
		try {

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = writer.writeValueAsString(product);

			loggerDebug("Line " + ii + " ********************");
			loggerDebug(json);

			return true;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
	}

	public ProductsRequestEntityData readEntityRecordFromJsonFile(String fileName) {
		String sMethod = "readEntityRecordFromJsonFile";
		loggerDebugM(sMethod, "start");
		try {
			
			ObjectMapper mapper = new ObjectMapper();

			ProductsRequestEntityData mp = mapper.readValue(new FileInputStream(fileName),
					ProductsRequestEntityData.class);
			new ProductRequestCsv2EntityMapper().debugRecord(mp, 0, "");
			new ProductRequestCsv2EntityMapper().writeRecord(mp, 0, getDebugJsonFileName());
			return mp;
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
