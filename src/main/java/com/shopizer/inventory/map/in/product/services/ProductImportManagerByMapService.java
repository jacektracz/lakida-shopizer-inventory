package com.shopizer.inventory.map.in.product.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.ProductSpecification;
import com.shopizer.inventory.entity.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.entity.in.product.model.ProductRequestMapData;

public class ProductImportManagerByMapService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportManagerByMapService.class);

	private String langs[] = { "en" };
	
	
	public boolean handleRecord(ProductRequestMapData entityData, PersistableProduct product, int ii, String imgBaseDir,String imgExt) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {
			ProductImportCoreByMapService pih = new ProductImportCoreByMapService();
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
			
			ProductImportManufacturerByMapService manufacturerHandler =  new ProductImportManufacturerByMapService();
			manufacturerHandler.handleManufacturer(entityData, product, specs);
					
			//ProductImportOptionsByMapService optionsByMapHandler =  new ProductImportOptionsByMapService();
			//optionsByMapHandler.handleDimensions(record, product);
			
			ProductImportOptionDimensionsByMapService dimesionsHandler =  new ProductImportOptionDimensionsByMapService();
			dimesionsHandler.handleDimensions(entityData, product, specs);
			
			ProductImportOptionsSizeByMapService optionsHandler =  new ProductImportOptionsSizeByMapService();
			optionsHandler.handleOptionsSize(entityData, product);
			optionsHandler.handleOptionsColour(entityData, product);
			
			ProductImportImageByMapService piis = new ProductImportImageByMapService();
			piis.handleImages(entityData, product, imgBaseDir,imgExt);

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
