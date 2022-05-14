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
	// private String langs[] = {"en","fr"};

	private static final String endPoint = "http://localhost:8080/api/v1/private/product?store=";

	private static final String MERCHANT = "DEFAULT";
	private static boolean DRY_RUN = false;
	private final int MAX_COUNT = 100;

	/**
	 * where to find csv
	 */

	private static String FILE_NAME = "C://lkd//ht//apps_java8_in_action//app//src//shopizer-inventory-csv//src//main//resources//product-loader2.csv";

	public static void main(String[] args) {

		ProductImport productsImport = new ProductImport();
		try {
			productsImport.importProducts(DRY_RUN, endPoint, MERCHANT, FILE_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void importProducts(boolean dryRun, String endpoint, String merchant, String fileName) throws Exception {
		String sMethod = "importProducts";
		loggerDebugM(sMethod, "start");
		ProductImportService pis = new ProductImportService();
		ProductImportController pic = new ProductImportController();
		int ii = 0;
		int count = 0;
		CSVParser parser = getParser(fileName);
		for (CSVRecord record : parser) {
			loggerDebugM(sMethod, "start-record:" + ii);
			PersistableProduct product = new PersistableProduct();

			boolean recordOk = pis.handleRecord(record, product, ii);
			if (!recordOk) {
				continue;
			}

			debugRecord(record, product, ii);

			pic.sendRecord(record, product, ii, dryRun, endpoint + merchant);
			boolean isOkCount = handleMaxCount(record, product, ii);
			if (!isOkCount) {
				break;
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
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader in = new BufferedReader(isr);

			@SuppressWarnings("resource")
			CSVParser parser = new CSVParser(in, format);

			return parser;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return null;
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
