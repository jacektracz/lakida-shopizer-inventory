package com.shopizer.inventory.map.in.product.services;

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
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;

public class ProductImportOptionsSizeByMapService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportOptionsSizeByMapService.class);

	public boolean handleOptionsColour(ProductRequestMapData record, PersistableProduct product) {
		return true;
	}
	public boolean handleOptionsSize(ProductRequestMapData record, PersistableProduct product) {
		String sMethod = "handleOptionsSize";
		loggerDebugM(sMethod, "start");
		try {
			if (!recordIsSetBoolean(record, "dimensions")) {
				return false;
			}
			String dimensionsOptions = recordGetString(record, "dimensions");

			if (StringUtils.isBlank(dimensionsOptions)) {
				return false;
			}
			PersistableProductOption opt = new PersistableProductOption();
			opt.setCode("size");
			List<String> dims = getTokensWithCollection(dimensionsOptions, ":");
			List<PersistableProductAttribute> attributes = new ArrayList<PersistableProductAttribute>();
			dims.stream().forEach(s -> {
				PersistableProductAttribute attr = new PersistableProductAttribute();
				attr.setOption(opt);
				PersistableProductOptionValue optValue = new PersistableProductOptionValue();
				optValue.setCode(s);

				attr.setOptionValue(optValue);

				attributes.add(attr);
			});
			product.setAttributes(attributes);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
		loggerDebugM(sMethod, "end");
		return true;
	}
	
	
	private List<String> getTokensWithCollection(String str, String token) {
		return Collections.list(new StringTokenizer(str, token)).stream().map(t -> (String) t)
				.collect(Collectors.toList());
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
