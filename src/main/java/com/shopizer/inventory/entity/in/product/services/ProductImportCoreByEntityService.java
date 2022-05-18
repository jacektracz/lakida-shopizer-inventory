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
import javax.swing.plaf.basic.BasicTreeUI.NodeDimensionsHandler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.shop.model.catalog.category.Category;
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

public class ProductImportCoreByEntityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportCoreByEntityService.class);

	private String langs[] = { "en" };

	public boolean handleCheckData(ProductRequestEntityData record, PersistableProduct product, int ii) {
		String sMethod = "handleDiscount";
		loggerDebugM(sMethod, "start");
		try {
			String code = record.getSku();

			if (StringUtils.isBlank(code)) {
				loggerDebug("Skipping code " + ii);
				return false;
			}

			String imp = record.getImportStatus();
			loggerDebug("Import status [" + imp + "]");

			if (StringUtils.isBlank(imp) || "no".equals(imp)) {
				loggerDebug("Skipping import " + ii);
				return false;
			}

			String categoryCode = record.getCategory();
			String price = record.getPrice();

			String orderString = record.getPosition();
			if (StringUtils.isBlank(orderString)) {
				orderString = String.valueOf(ii);
			}

			int order = Integer.parseInt(orderString);

			if (StringUtils.isBlank(categoryCode)) {
				loggerDebug("No category, skipping " + code);
				return false;
			}

			if (StringUtils.isBlank(price)) {
				loggerDebug("No price, skipping " + code);
				return false;
			} else {
				// int randomPrice = this.randPrice(150, 300);//when there is no price
			}
			loggerDebugM(sMethod, "end");
			return true;

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
	}

	public boolean handleDiscount(ProductRequestEntityData record, PersistableProduct product,
			PersistableProductPrice persistableProductPrice, BigDecimal productPrice) {
		String sMethod = "handleDiscount";
		loggerDebugM(sMethod, "start");
		try {
			String discount = record.getDeal();
			if (StringUtils.isBlank(discount)) {
				loggerDebug("No discount " + discount);
				return false;
			}
			BigDecimal discountedPrice = this.createDiscountedPrice(productPrice, discount);
			if (discountedPrice != null) {
				persistableProductPrice.setDiscountedPrice(discountedPrice);
			}

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	public boolean handleDescriptions(ProductRequestEntityData record, PersistableProduct product) {
		String sMethod = "handleImages";
		loggerDebugM(sMethod, "start");
		try {
			List<ProductDescription> descriptions = new ArrayList<ProductDescription>();
			for (int langLenth = 0; langLenth < langs.length; langLenth++) {

				ProductDescription description = new ProductDescription();
				String lang = langs[langLenth];
				String strPreOrder = record.getPreOrder();
				if (!StringUtils.isBlank(strPreOrder)) {
					// something specific must be written ?
				}
				description = new ProductDescription();
				description.setLanguage(lang);
				description.setTitle(cleanup(record.getNameEn()));
				description.setName(cleanup(record.getNameEn()));
				description.setDescription(cleanup(record.getDescriptionEn()));
				if (StringUtils.isBlank(description.getName())) {
					description.setName(description.getDescription());
				}
				description.setFriendlyUrl(minimalFriendlyUrlCreator(description.getName()));
				descriptions.add(description);

			}
			product.setDescriptions(descriptions);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	private String handleBarcode(ProductRequestEntityData record, PersistableProduct product, boolean simple) {
		String sMethod = "handleBarcode";
		loggerDebugM(sMethod, "start");
		try {
			String barcode = "";
			barcode = record.getBarcode();

			if (StringUtils.isBlank(barcode)) {
				return "";
			}

			if (simple) {
				loggerDebugM(sMethod, "end:" + barcode);
				return barcode;
			}

			loggerDebugM(sMethod, "end:" + barcode);
			return barcode;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return "";
	}

	public boolean handleProductData(ProductRequestEntityData record, PersistableProduct product,
			ProductSpecification specs, int ii) {
		String sMethod = "handleProductData";
		loggerDebugM(sMethod, "start");
		try {
			String code = record.getSku();
			product.setSku(code);
			boolean simple = true;
			String barcode = handleBarcode(record, product, simple);
			product.setRefSku(barcode);
			product.setProductSpecifications(specs);

			Category category = new Category();
			String categoryCode = record.getCategory();
			category.setCode(categoryCode);
			List<Category> categories = new ArrayList<Category>();
			categories.add(category);

			product.setCategories(categories);

			String orderString = record.getPosition();
			if (StringUtils.isBlank(orderString)) {
				orderString = String.valueOf(ii);
			}

			int order = Integer.parseInt(orderString);
			product.setSortOrder(order);// set the order or iterator as sort order
			product.setAvailable(true);// force availability
			product.setProductVirtual(false);// force tangible good
			product.setQuantityOrderMinimum(1);// force to 1 minimum when ordering
			product.setProductShipeable(true);// all items are shipeable

			String type = record.getProductType();
			product.setType(type);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	public boolean handleCategoryCode(ProductRequestEntityData record, PersistableProduct product, int ii) {
		String sMethod = "handleCategoryCode";
		loggerDebugM(sMethod, "start");
		try {
			String categoryCode = record.getCategory();
			Category category = new Category();
			category.setCode(categoryCode);
			List<Category> categories = new ArrayList<Category>();
			categories.add(category);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}


	public boolean handleProductPrice(ProductRequestEntityData record, PersistableProduct product) {
		String sMethod = "handleProductPrice";
		loggerDebugM(sMethod, "start");
		try {
			String price = record.getPrice();
			price = price.replaceAll(",", ".").trim();
			BigDecimal productPrice = new BigDecimal(price);

			PersistableProductPrice persistableProductPrice = new PersistableProductPrice();
			persistableProductPrice.setDefaultPrice(true);

			persistableProductPrice.setOriginalPrice(productPrice);

			
			String discount = record.getDeal();
			BigDecimal discountedPrice = this.createDiscountedPrice(productPrice, discount);
			if (discountedPrice != null) {
				persistableProductPrice.setDiscountedPrice(discountedPrice);
			}

			List<PersistableProductPrice> productPriceList = new ArrayList<PersistableProductPrice>();
			productPriceList.add(persistableProductPrice);
			product.setProductPrices(productPriceList);

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	public boolean handleQuantity(ProductRequestEntityData record, PersistableProduct product) {
		String sMethod = "handleImages";
		loggerDebugM(sMethod, "start");
		try {
			String stringQuantity = record.getQuantity();
			if (StringUtils.isBlank(stringQuantity)) {
				stringQuantity = "1";
			}
			int quantity = Integer.parseInt(stringQuantity);

			product.setQuantity(quantity);
			product.setQuantityOrderMaximum(maximumQuantity(quantity));
			product.setQuantityOrderMinimum(minimumQuantity(quantity));

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	private String cleanup(String field) {
		return field.replaceAll("�", "");
	}

	private BigDecimal createDiscountedPrice(BigDecimal price, String discount) {

		if (StringUtils.isNotBlank(discount) && !discount.equals("0")) {
			double percent = 1 - Double.parseDouble(discount) / 100d;
			price = price.multiply(new BigDecimal(percent));
			return roundAmount(price);
		}

		return null;

	}

	private BigDecimal roundAmount(BigDecimal amount) {
		BigDecimal scaled = amount.setScale(0, RoundingMode.HALF_UP);
		return scaled;
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

	public byte[] extractBytes2(File imgPath, String extension) throws Exception {
		String sMethod = "extractBytes2";
		loggerDebugM(sMethod, "start");
		if (!imgPath.exists()) {
			dbgImgNotFound(imgPath);
			return null;
		}

		try {

			BufferedImage bImage = ImageIO.read(imgPath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			boolean writeOk = ImageIO.write(bImage, extension, baos);
			if (!writeOk) {
				loggerDebugM(sMethod, "end-no-image");
				return null;
			}
			byte[] bytes = baos.toByteArray();
			loggerDebugM(sMethod, "end");

			return bytes;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "exception", ex);
			loggerDebugM(sMethod, "exception");
			return null;
		}
	}

	public byte[] extractBytes1(File imgPath) throws Exception {

		if (!imgPath.exists()) {
			loggerDebug("--------------------------------------");
			loggerDebug("IMAGE NOT FOUND " + imgPath.getName());
			loggerDebug("IMAGE PATH " + imgPath.getAbsolutePath());
			loggerDebug("--------------------------------------");
			return null;
		}

		FileInputStream fis = null;
		BufferedInputStream inputStream = null;
		try {
			fis = new FileInputStream(imgPath);
			inputStream = new BufferedInputStream(fis);
			byte[] fileBytes = new byte[(int) imgPath.length()];
			inputStream.read(fileBytes);
			return fileBytes;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (fis != null) {
				fis.close();
			}
		}

	}

	public int randPrice(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public String minimalFriendlyUrlCreator(String productName) {
		productName = productName.toLowerCase();
		productName = productName.replace("  ", " ");

		// remove accents
		productName = Normalizer.normalize(productName, Normalizer.Form.NFD);
		productName = productName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

		productName = productName.replace(" ", "-");
		return productName;
	}

	public int maximumQuantity(int quantity) {
		if (quantity == 1) {
			return quantity;
		}

		if (quantity > 0) {
			return quantity++;
		}

		return quantity;
	}

	public int minimumQuantity(int quantity) {

		return Integer.parseInt("1");
	}

}
