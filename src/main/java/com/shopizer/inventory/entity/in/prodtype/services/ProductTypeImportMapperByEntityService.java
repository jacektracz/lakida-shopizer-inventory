package com.shopizer.inventory.entity.in.prodtype.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.type.PersistableProductType;
import com.salesmanager.shop.model.catalog.product.type.ProductTypeDescription;
import com.shopizer.inventory.entity.in.prodtype.model.ProductTypeRequestEntityData;


public class ProductTypeImportMapperByEntityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeImportMapperByEntityService.class);

	private String langs[] = { "en" };

	public boolean handleRecord(ProductTypeRequestEntityData entityData, PersistableProductType itemToSendt, int ii,
			String imgBaseDir, String imgExt) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {
			PersistableProductType manufacturer = new PersistableProductType();
			manufacturer.setCode(entityData.getCode());
			manufacturer.setId(null);

			List<ProductTypeDescription> descriptions = new ArrayList<ProductTypeDescription>();

			
						
			ProductTypeDescription descriptionEn = new ProductTypeDescription();
			descriptionEn.setLanguage("en");
			descriptionEn.setName(entityData.getNameEn());
			descriptionEn.setDescription(entityData.getNameEn());	

			descriptions.add(descriptionEn);
			
			//add other description
			ProductTypeDescription descriptionPl = new ProductTypeDescription();
			descriptionPl.setId(null);
			descriptionPl.setLanguage("pl");
			descriptionPl.setName(entityData.getNameEn());
			descriptionPl.setDescription(entityData.getNameEn());	
			
			descriptions.add(descriptionPl);
			manufacturer.setDescriptions(descriptions);
			
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
