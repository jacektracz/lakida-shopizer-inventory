package com.shopizer.inventory.map.in.product.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.ProductSpecification;
import com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValue;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;

public class ProductImportOptionDimensionsByMapService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportOptionDimensionsByMapService.class);
		
	public boolean handleDimensions(ProductRequestMapData record, PersistableProduct product, ProductSpecification specs) {
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
