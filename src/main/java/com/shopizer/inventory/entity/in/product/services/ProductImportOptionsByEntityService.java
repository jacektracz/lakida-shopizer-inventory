package com.shopizer.inventory.entity.in.product.services;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.category.Category;
import com.salesmanager.shop.model.catalog.product.PersistableImage;
import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.PersistableProductPrice;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.ProductSpecification;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOption;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOptionValue;
import com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValue;
import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestOptionData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestOptionsGroupData;

public class ProductImportOptionsByEntityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportOptionsByEntityService.class);

	public boolean handleOptionsSize(ProductRequestEntityData entityData, PersistableProduct product) {
		String sMethod = "handleOptionsSize";
		loggerDebugM(sMethod, "start");
		try {
			
			List<PersistableProductAttribute> attributes = new ArrayList<PersistableProductAttribute>();
			fiilAttributes(entityData.getSizeOptions(),attributes,"SIZE");			
			product.setAttributes(attributes);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
		loggerDebugM(sMethod, "end");
		return true;
	}
	
	public boolean handleOptionsColour(ProductRequestEntityData entityData, PersistableProduct product) {
		String sMethod = "handleOptionsColour";
		loggerDebugM(sMethod, "start");
		try {
			
			List<PersistableProductAttribute> attributes = new ArrayList<PersistableProductAttribute>();
			fiilAttributes(entityData.getSizeOptions(),attributes,"COLOUR");			
			product.setAttributes(attributes);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	private boolean fiilAttributes(
			ProductRequestOptionsGroupData entityData,
			List<PersistableProductAttribute> attributes
			,String optionCode) {
		String sMethod = "fiilAttributes";
		loggerDebugM(sMethod, "start");
		
		try {
			PersistableProductOption opt = new PersistableProductOption();
			opt.setCode(entityData.getOptionType());
			
			for (ProductRequestOptionData dataItem : entityData.getProductOptions()) {
				PersistableProductAttribute attr = new PersistableProductAttribute();
				attr.setOption(opt);
				PersistableProductOptionValue optValue = new PersistableProductOptionValue();
				optValue.setCode(dataItem.getOptionCode());
				optValue.setName(dataItem.getOptionName());
				attr.setOptionValue(optValue);
				attributes.add(attr);
			}
			
			loggerDebugM(sMethod, "end");
			return true;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
		
		
	}

	public boolean handleDimensions(ProductRequestMapData record, PersistableProduct product,
			ProductSpecification specs) {
		String sMethod = "handleDimensions";
		loggerDebugM(sMethod, "start");
		try {
			String manufacturer = recordGetString(record, "collection");// brand - manufacturer ...
			specs.setManufacturer(manufacturer);

			// sizes are required when loading a product
			String dimensions = recordGetString(record, "dimension");

			String W = convertDimension(recordGetString(record, "package_width"), dimensions).toString();
			String L = convertDimension(recordGetString(record, "package_length"), dimensions).toString();
			String H = convertDimension(recordGetString(record, "package_length"), dimensions).toString();
			loggerDebugM(sMethod, "W:" + W + " L" + L + " H:" + H);

			specs.setHeight(convertDimension(recordGetString(record, "package_height"), dimensions));
			specs.setWidth(convertDimension(recordGetString(record, "package_width"), dimensions));
			specs.setLength(convertDimension(recordGetString(record, "package_length"), dimensions));
			specs.setWeight(convertWeight(recordGetString(record, "package_weight")));
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	
	private BigDecimal convertDimension(String value, String dimensions) {

		// in our case we convert from cm to inches 1 * 0.393701
		if (StringUtils.isBlank(value)) {
			value = "0";
		}
		value = value.replaceAll(",", ".").trim();
		// loggerDebug("Dimension " + value);
		BigDecimal decimalValue = new BigDecimal(value);

		if (StringUtils.isBlank(dimensions) || "CM".equals(dimensions)) {
			decimalValue = decimalValue.multiply(new BigDecimal("0.393701"));
		}

		BigDecimal scaled = decimalValue.setScale(0, RoundingMode.HALF_UP);
		return scaled;

	}

	private BigDecimal convertWeight(String value) {

		// in our case we already have the good weight
		if (StringUtils.isBlank(value)) {
			value = "0";
		}
		value = value.replaceAll(",", ".").trim();
		BigDecimal decimalValue = new BigDecimal(value);
		BigDecimal scaled = decimalValue.setScale(0, RoundingMode.HALF_UP);
		// decimalValue = decimalValue.multiply(new BigDecimal("0.393701"));
		return scaled;

	}

	private ProductOptionValue optValue(String code) {
		ProductOptionValue optValue = new ProductOptionValue();
		optValue.setCode(code);
		return optValue;
	}

	private boolean recordIsSetBoolean(ProductRequestMapData record, String key) {
		String sMethod = "recordIsSetBoolean";
		loggerDebugM(sMethod, "start:" + key);
		boolean valueret = record.recordIsSetBoolean(key);
		loggerDebugM(sMethod, "end:" + key + ":" + valueret);
		return valueret;
	}

	private String recordGetString(ProductRequestMapData record, String key) {
		String sMethod = "recordGetString";
		loggerDebugM(sMethod, "start");
		String valueret = record.recordGetString(key, "");
		loggerDebugM(sMethod, "end");
		return valueret;
	}

	public void imageTestWrite() {
		BufferedImage bImage = null;
		try {
			String fullPath = "C:\\lkd\\ht\\apps_shopizer_l\\src\\app\\shopizer\\sm-shop\\";
			String initialImageName = fullPath + "files/images/img__ini.png";
			String createdImageName = fullPath + "files/images/img__ini_created.jpg";
			File initialImage = new File(initialImageName);
			loggerDebug("test-image-initial-image-exists :" + initialImage.exists());

			bImage = ImageIO.read(initialImage);
			loggerDebug("test-image-bImage-getHeight :" + bImage.getHeight());
			loggerDebug("test-image-bImage-getWidth :" + bImage.getWidth());
			File createdFile = new File(createdImageName);
			ImageIO.write(bImage, "jpg", createdFile);
			loggerDebug("test-image-created-path :" + createdFile.getAbsolutePath());
			loggerDebug("test-image-created-exists :" + createdFile.exists());

			String fullCreatedPath2 = "C:\\lkd\\";
			String createdImageName2 = fullCreatedPath2 + "files\\images\\img__ini_created.png";
			File createdFile2 = new File(createdImageName2);
			ImageIO.write(bImage, "png", createdFile2);
			loggerDebug("test-image-created2-path :" + createdFile2.getAbsolutePath());
			loggerDebug("test-image-created2-exists :" + createdFile2.exists());

		} catch (Exception e) {
			loggerDebug("Exception occured :" + e.getMessage());
		}

		loggerDebug("Images were written succesfully.");
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
