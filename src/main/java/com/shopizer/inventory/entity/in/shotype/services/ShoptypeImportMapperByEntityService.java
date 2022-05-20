package com.shopizer.inventory.entity.in.shotype.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription;
import com.salesmanager.shop.model.catalog.manufacturer.PersistableManufacturer;
import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.shopizer.inventory.entity.in.shotype.model.ShoptypeRequestEntityData;


public class ShoptypeImportMapperByEntityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoptypeImportMapperByEntityService.class);

	private String langs[] = { "en" };

	public boolean handleRecord(ShoptypeRequestEntityData entityData, PersistableManufacturer itemToSendt, int ii,
			String imgBaseDir, String imgExt) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {
			PersistableManufacturer manufacturer = new PersistableManufacturer();
			manufacturer.setCode(entityData.getCode());
			manufacturer.setId(null);

			List<ManufacturerDescription> descriptions = new ArrayList<ManufacturerDescription>();
			
			ManufacturerDescription descriptionEn = new ManufacturerDescription();
			descriptionEn.setLanguage("en");
			descriptionEn.setName(entityData.getNameEn());
			descriptionEn.setDescription(entityData.getNameEn());	

			descriptions.add(descriptionEn);
			
			//add other description
			ManufacturerDescription descriptionPl = new ManufacturerDescription();
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
