package com.shopizer.inventory.entity.in.product.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.entity.in.cat.model.CategoriesRequestEntityData;
import com.shopizer.inventory.entity.in.cat.model.CategoryRequestEntityData;
import com.shopizer.inventory.entity.in.cat.services.CategoryImportLocalStorage;
import com.shopizer.inventory.entity.in.cat.services.CategoryRequestEntityReader;
import com.shopizer.inventory.entity.in.cat.services.CategoryRequestEntityWriter;
import com.shopizer.inventory.entity.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.entity.in.product.model.ProductRequestImageData;
import com.shopizer.inventory.entity.in.product.model.ProductRequestOptionData;
import com.shopizer.inventory.entity.in.product.model.ProductRequestOptionsGroupData;
import com.shopizer.inventory.entity.in.product.model.ProductsRequestEntityData;

public class ProductRequestCategoriesProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRequestCategoriesProducer.class);

	private static String getImportJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = ProductImportSettings.getImportFileBaseDir() + "categories-retrieved-" + idx + ".json";
		}
		return sF;
	}

	public static void main(String[] args) {

		try {

			int ii = 0;

			if (ii == 0) {
				ProductRequestCategoriesProducer productsImport = new ProductRequestCategoriesProducer();
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

			String fn = getImportJsonFileName("ld-4");

			importProducts(false, fn);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
	}

	public void importProducts(boolean dryRun, String fileName) throws Exception {

		String sMethod = "importProducts";
		loggerDebugM(sMethod, "start");
		try {
			String fn = fileName;
			ProductRequestEntityReader entityReader = new ProductRequestEntityReader();
			ProductsRequestEntityData entityData = entityReader.readEntityRecordFromJsonFile(fn);

			if (entityData == null) {
				loggerDebugM(sMethod, "end");
				return;
			}

			CategoryImportLocalStorage localStorage = new CategoryImportLocalStorage();
			fillStorage(localStorage, entityData);
			List<CategoryRequestEntityData> lst = new ArrayList<>();
			fillCategories(localStorage, lst);
			CategoriesRequestEntityData dataToSave = new CategoriesRequestEntityData();
			dataToSave.setCategoryItems(lst);

			String savedDataFile = getImportJsonFileName("saved-1");
			CategoryRequestEntityWriter writerHandler = new CategoryRequestEntityWriter();
			writerHandler.writeRecord(dataToSave, 0, savedDataFile);
			loggerDebugM(sMethod, "end");
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}

	}

	public void fillStorage(CategoryImportLocalStorage localStorage, ProductsRequestEntityData entityData) {

		String sMethod = "fillStorage";
		loggerDebugM(sMethod, "start");
		List<ProductRequestEntityData> products = entityData.getProductItems();

		for (ProductRequestEntityData dt : products) {
			if (dt.getCategories() == null) {
				continue;
			}
			if (dt.getCategories().getCategoryItems() == null) {
				continue;
			}
			for (CategoryRequestEntityData catItem : dt.getCategories().getCategoryItems()) {
				localStorage.addCat(catItem);
			}
		}
		loggerDebugM(sMethod, "start");
	}

	public void fillCategories(CategoryImportLocalStorage localStorage, List<CategoryRequestEntityData> lstOut) {

		String sMethod = "fillStorage";
		loggerDebugM(sMethod, "start");
		Collection<CategoryRequestEntityData> items = localStorage.getCategoriesMap().values();
		lstOut.addAll(items);
		loggerDebugM(sMethod, "start");
	}

	private String getDbgClassName() {
		return "ProductRequestEntityProducer::";
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
