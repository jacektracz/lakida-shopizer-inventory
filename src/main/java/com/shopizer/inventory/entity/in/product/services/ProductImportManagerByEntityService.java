package com.shopizer.inventory.entity.in.product.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.ProductSpecification;
import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;

public class ProductImportManagerByEntityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportManagerByEntityService.class);

	private String langs[] = { "en" };

	public boolean handleRecord(ProductRequestEntityData entityData, PersistableProduct product, int ii,
			String imgBaseDir, String imgExt) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {
			ProductImportCoreByEntityService pih = new ProductImportCoreByEntityService();
			boolean dataOk = pih.handleCheckData(entityData, null, ii);
			if (!dataOk) {
				return false;
			}

			ProductSpecification specs = new ProductSpecification();

			pih.handleQuantity(entityData, product);

			pih.handleProductPrice(entityData, product);

			pih.handleDescriptions(entityData, product);
			pih.handleCategoryCode(entityData, product, ii);

			pih.handleProductData(entityData, product, specs, ii);

			ProductImportManufacturerByEntityService manufacturerHandler = new ProductImportManufacturerByEntityService();
			manufacturerHandler.handleManufacturer(entityData, product, specs);

			// ProductImportOptionsByMapService optionsByMapHandler = new
			// ProductImportOptionsByMapService();
			// optionsByMapHandler.handleDimensions(record, product);

			ProductImportOptionDimensionsByEntityService dimesionsHandler = new ProductImportOptionDimensionsByEntityService();
			dimesionsHandler.handleDimensions(entityData, product, specs);
			
			boolean handleOptions = false;
			if (handleOptions) {
				ProductImportOptionsByEntityService optionsHandler = new ProductImportOptionsByEntityService();
				optionsHandler.handleOptionsSize(entityData, product);
				optionsHandler.handleOptionsColour(entityData, product);
			}
			
			ProductImportImageByEntityService piis = new ProductImportImageByEntityService();
			piis.handleImages(entityData, product, imgBaseDir, imgExt);

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

	private void dbgImgNotFound(File imgPath) {
		String sMethod = "extractBytes2";
		loggerDebugM(sMethod, "start");

		loggerDebug("--------------------------------------");
		loggerDebug("IMAGE NOT FOUND " + imgPath.getName());
		loggerDebug("IMAGE PATH " + imgPath.getAbsolutePath());
		loggerDebug("--------------------------------------");
		loggerDebugM(sMethod, "return");

	}

}
