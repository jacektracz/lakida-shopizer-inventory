package com.shopizer.inventory.entity.in.cat.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.category.Category;
import com.salesmanager.shop.model.catalog.category.CategoryDescription;
import com.salesmanager.shop.model.catalog.category.PersistableCategory;
import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.shopizer.inventory.entity.in.cat.model.CategoryRequestEntityData;


public class CategoryImportMapperByEntityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryImportMapperByEntityService.class);

	private String langs[] = { "en" };

	public boolean handleRecord(Map<String,PersistableCategory> categoryMap, CategoryRequestEntityData entityData, PersistableCategory record, int ii,
			String imgBaseDir, String imgExt) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {
			PersistableCategory category = new PersistableCategory();
			category.setCode(entityData.getCode());
			category.setSortOrder(Integer.parseInt(entityData.getRecordPosition()));
			category.setVisible(Integer.parseInt(entityData.getRecordVisible())==1?true:false);
			
			List<CategoryDescription> descriptions = new ArrayList<CategoryDescription>();
			
			//add english description
			CategoryDescription description = new CategoryDescription();
			description.setLanguage("en");
			description.setTitle(entityData.getTitleEn());
			description.setName(entityData.getNameEn());
			description.setDescription(description.getName());
			description.setFriendlyUrl(entityData.getFriendlyUrlEn());
			//description.setHighlights(record.get("highlights_en"));
			
			descriptions.add(description);
			
			//add french description
			description = new CategoryDescription();
			description.setLanguage("pl");
			description.setTitle(entityData.getTitleEn());
			description.setName(entityData.getNameEn());
			description.setDescription(description.getName());
			description.setFriendlyUrl(entityData.getFriendlyUrlEn());
			//description.setHighlights(record.get("highlights_fr"));
			
			descriptions.add(description);
			category.setDescriptions(descriptions);
			
			categoryMap.put(category.getCode(), category);
			
			if(!StringUtils.isBlank(entityData.getRecordParent())) {
				PersistableCategory parent = categoryMap.get(entityData.getRecordParent());
				if(parent!=null) {
					Category parentCategory = new Category();
					parentCategory.setCode(parent.getCode());
					category.setParent(parentCategory);
					parent.getChildren().add(category);
				}
			}			
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
