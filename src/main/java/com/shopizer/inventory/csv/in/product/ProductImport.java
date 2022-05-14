package com.shopizer.inventory.csv.in.product;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;

public class ProductImport {
	private String IMAGE_EXT = "PNG";
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImport.class);

	/**
	 * Fields used in xls file with Product mapping
	 * 
	 * barcode sku image image_file name_<lang> description_<lang> quantity
	 * collection category height width length weight price deal -> % discounted
	 * pre-order dimensions -> CM | IN (centimeters or inches) position -> placement
	 * in listing page (ordering) import -> yes | no
	 * 
	 * NEED JAVA 1.8
	 */

	/**
	 * Supported languages CSV template must contain name_<language> and
	 * description<language>
	 */
	private static final String endPoint = "http://localhost:8080/api/v1/private/product?store=";

	private static final String MERCHANT = "DEFAULT";
	private static boolean DRY_RUN = false;
	private final int MAX_COUNT = 100;

	/**
	 * where to find csv
	 */

	private static String getFileName() {
		int ii = 1;
		String sF = "";
		if(ii == 0) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader2.csv";
		}
		if(ii == 1) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader3.md";
		}
		if(ii == 2) {
			sF = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader.csv";
		}
		if(ii == 3) {
			sF = "C://lkd//ht-shopizer//import//product-loader2.csv";
		}
		if(ii == 4) {
			sF = "C://lkd//ht-shopizer//import//product-loader3.md";
		}				
		return sF;
	}

	private static String getImgBaseDir() {
		//String IMAGE_BASE_DIR = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//imgs//";
		String IMAGE_BASE_DIR = "C://lkd//ht-shopizer//imgs//";
		return IMAGE_BASE_DIR;
	}

	public static void main(String[] args) {

		ProductImport productsImport = new ProductImport();
		try {
			
			int ii = 0;
			
			if(ii == 0) {
				productsImport.mainImport();
			}
			
			if(ii == 1) {			
				productsImport.mainCheck();
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
			
			String fn = getFileName();
			String ibd = getImgBaseDir();
			ProductImport productsImport = new ProductImport();
			productsImport.importProducts(DRY_RUN, endPoint, MERCHANT, fn, ibd,IMAGE_EXT);
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
	}
	
	public void mainCheck() {
		String sMethod = "importProducts";
		loggerDebugM(sMethod, "start");

		ProductImportService productsImport = new ProductImportService();
		try {			
			String ibd = getImgBaseDir();
			productsImport.handleCheckImages( ibd,"img_2",IMAGE_EXT);
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
	}

	public void importProducts(boolean dryRun, String endpoint, String merchant, 
			String fileName, String imgBaseDir,
			String imgExt)
			throws Exception {
		
		String sMethod = "importProducts";
		loggerDebugM(sMethod, "start");
		
		int ii = 0;
		int count = 0;
		CSVParser parser = getParser(fileName);
		if(parser == null) {
			loggerDebugM(sMethod, "end");
			return;
		}
		
		for (CSVRecord record : parser) {
			loggerDebugM(sMethod, "start-record:" + ii);

			boolean isOk = handleCsvRecord(record, ii, dryRun, endpoint, merchant, fileName, imgBaseDir,imgExt);
			if (isOk) {
				count++;
			}

			loggerDebugM(sMethod, "end-record:" + ii);
		}

		loggerDebug("------------------------------------");
		loggerDebug("Product import done " + count + " Dry Run [" + DRY_RUN + "]");
		loggerDebug("------------------------------------");
		loggerDebugM(sMethod, "end");
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
			if(parser == null) {
				loggerDebugM(sMethod, "parser-is-null-filename:" + fileName);
				return parser;
			}
			
			if(parser.getRecordNumber() == 0) {
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

	public boolean handleCsvRecord(CSVRecord record, int ii, boolean dryRun, String endpoint, String merchant,
			String fileName, String imgBaseDir, String imgExt) {
		
		String sMethod = "handleCsvRecord";
		loggerDebugM(sMethod, "start");
		try {
			ProductImportService pis = new ProductImportService();
			ProductImportController pic = new ProductImportController();

			loggerDebugM(sMethod, "start-record:" + ii);
			PersistableProduct product = new PersistableProduct();

			boolean recordOk = pis.handleRecord(record, product, ii, imgBaseDir,imgExt);
			if (!recordOk) {
				loggerDebugM(sMethod, "end-false-record:" + ii);
				return false;
			}

			debugRecord(record, product, ii);

			pic.sendRecord(record, product, ii, dryRun, endpoint + merchant);
			boolean isOkCount = handleMaxCount(record, product, ii);
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

	public boolean handleMaxCount(CSVRecord record, PersistableProduct product, int ii) {
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

	public boolean debugRecord(CSVRecord record, PersistableProduct product, int ii) {
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
