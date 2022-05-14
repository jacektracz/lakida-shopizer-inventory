package com.shopizer.inventory.csv.in.product;

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

public class ProductImportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportService.class);

	private String langs[] = { "en" };
	
	
	public boolean handleRecord(CSVRecord record, PersistableProduct product, int ii, String imgBaseDir,String imgExt) {
		String sMethod = "handleRecord";
		loggerDebugM(sMethod, "start");
		try {

			ProductImportService pih = new ProductImportService();
			boolean dataOk = pih.handleCheckData(record, null, ii);
			if (!dataOk) {
				return false;
			}

			ProductSpecification specs = new ProductSpecification();

			pih.handleCategoryCode(record, product, ii);

			pih.handleDimensions(record, product, specs);

			pih.handleProductData(record, product, specs, ii);

			pih.handleDimensions(record, product);

			pih.handleImages(record, product, imgBaseDir,imgExt);

			pih.handleQuantity(record, product);

			pih.handleProductPrice(record, product);

			pih.handleDescriptions(record, product);
			return true;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
			return false;
		}
	}

	public boolean handleCheckData(CSVRecord record, PersistableProduct product, int ii) {
		String sMethod = "handleDiscount";
		loggerDebugM(sMethod, "start");
		try {
			String code = recordGetString(record, "sku");

			if (StringUtils.isBlank(code)) {
				loggerDebug("Skipping code " + ii);
				return false;
			}

			String barcode = handleBarcode(record, null, true);
			if (barcode.equals("")) {
				return false;
			}

			String imp = recordGetString(record, "import");
			loggerDebug("Import status [" + imp + "]");

			if (StringUtils.isBlank(imp) || "no".equals(imp)) {
				loggerDebug("Skipping import " + ii);
				return false;
			}

			String categoryCode = recordGetString(record, "category");
			String price = recordGetString(record, "price");

			String orderString = recordGetString(record, "position");
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

	public boolean handleDiscount(CSVRecord record, PersistableProduct product,
			PersistableProductPrice persistableProductPrice, BigDecimal productPrice) {
		String sMethod = "handleDiscount";
		loggerDebugM(sMethod, "start");
		try {
			if (!recordIsSetBoolean(record, "deal")) {
				return false;
			}
			String discount = recordGetString(record, "deal");
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

	public boolean handleDescriptions(CSVRecord record, PersistableProduct product) {
		String sMethod = "handleImages";
		loggerDebugM(sMethod, "start");
		try {
			List<ProductDescription> descriptions = new ArrayList<ProductDescription>();
			for (int langLenth = 0; langLenth < langs.length; langLenth++) {

				ProductDescription description = new ProductDescription();
				String lang = langs[langLenth];
				if (!recordIsSetBoolean(record, "pre")) {
					// something specific must be written ?
				}
				String strPreOrder = recordGetString(record, "pre-order");
				if (!StringUtils.isBlank(strPreOrder)) {
					// something specific must be written ?
				}
				description = new ProductDescription();
				description.setLanguage(lang);
				description.setTitle(cleanup(recordGetString(record, "name_" + lang)));
				description.setName(cleanup(recordGetString(record, "name_" + lang)));
				description.setDescription(cleanup(recordGetString(record, "description_" + lang)));
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

	private String handleBarcode(CSVRecord record, PersistableProduct product, boolean simple) {
		String sMethod = "handleBarcode";
		loggerDebugM(sMethod, "start");
		try {
			String barcode = "";
			if (!recordIsSetBoolean(record, "barcode")) {
				return "";
			}
			barcode = recordGetString(record, "barcode");

			if (StringUtils.isBlank(barcode)) {
				return "";
			}

			if (simple) {
				loggerDebugM(sMethod, "end:" + barcode);
				return barcode;
			}

			barcode = this.alternativeIdentifier(record);
			if (StringUtils.isBlank(barcode)) {
				loggerDebug("Skipping barcode ");
				return "";
			}

			loggerDebugM(sMethod, "end:" + barcode);
			return barcode;
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return "";
	}

	public boolean handleProductData(CSVRecord record, PersistableProduct product, ProductSpecification specs, int ii) {
		String sMethod = "handleProductData";
		loggerDebugM(sMethod, "start");
		try {
			String code = recordGetString(record, "sku");
			product.setSku(code);
			boolean simple = true;
			String barcode = handleBarcode(record, product, simple);
			product.setRefSku(barcode);
			product.setProductSpecifications(specs);

			Category category = new Category();
			String categoryCode = recordGetString(record, "category");
			category.setCode(categoryCode);
			List<Category> categories = new ArrayList<Category>();
			categories.add(category);

			product.setCategories(categories);

			String orderString = recordGetString(record, "position");
			if (StringUtils.isBlank(orderString)) {
				orderString = String.valueOf(ii);
			}

			int order = Integer.parseInt(orderString);
			product.setSortOrder(order);// set the order or iterator as sort order
			product.setAvailable(true);// force availability
			product.setProductVirtual(false);// force tangible good
			product.setQuantityOrderMinimum(1);// force to 1 minimum when ordering
			product.setProductShipeable(true);// all items are shipeable

			if (recordIsSetBoolean(record, "type")) {
				product.setType(recordGetString(record, "type"));
			}

		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	public boolean handleDimensions(CSVRecord record, PersistableProduct product, ProductSpecification specs) {
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

	public boolean handleCategoryCode(CSVRecord record, PersistableProduct product, int ii) {
		String sMethod = "handleCategoryCode";
		loggerDebugM(sMethod, "start");
		try {
			String categoryCode = recordGetString(record, "category");
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

	private boolean recordIsSetBoolean(CSVRecord record, String key) {
		String sMethod = "recordIsSetBoolean";
		loggerDebugM(sMethod, "start:" + key);

		if (!record.isSet(key)) {
			loggerDebugM(sMethod, "end:" + key + ":" + "false");
			return false;
		}
		loggerDebugM(sMethod, "end:" + key + ":" + "true");
		return true;
	}

	private String recordGetString(CSVRecord record, String key) {
		String sMethod = "recordGetString";
		loggerDebugM(sMethod, "start");

		if (!recordIsSetBoolean(record, key)) {
			return "";
		}
		String value = record.get(key);
		loggerDebugM(sMethod, "value:" + value);
		loggerDebugM(sMethod, "end");
		return value;
	}

	public boolean handleDimensions(CSVRecord record, PersistableProduct product) {
		String sMethod = "handleDimensions";
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

	public boolean handleX2(CSVRecord record, PersistableProduct product) {
		String sMethod = "handleImages";
		loggerDebugM(sMethod, "start");
		try {
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	public boolean handleX3(CSVRecord record, PersistableProduct product) {
		String sMethod = "handleImages";
		loggerDebugM(sMethod, "start");
		try {
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	public boolean handleProductPrice(CSVRecord record, PersistableProduct product) {
		String sMethod = "handleProductPrice";
		loggerDebugM(sMethod, "start");
		try {
			String price = recordGetString(record, "price");
			price = price.replaceAll(",", ".").trim();
			BigDecimal productPrice = new BigDecimal(price);

			PersistableProductPrice persistableProductPrice = new PersistableProductPrice();
			persistableProductPrice.setDefaultPrice(true);

			persistableProductPrice.setOriginalPrice(productPrice);

			if (!recordIsSetBoolean(record, "deal")) {
				loggerDebugM(sMethod, "end-1");
				return false;
			}
			String discount = recordGetString(record, "deal");
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

	public boolean handleQuantity(CSVRecord record, PersistableProduct product) {
		String sMethod = "handleImages";
		loggerDebugM(sMethod, "start");
		try {
			String stringQuantity = recordGetString(record, "quantity");
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

	public boolean handleCheckImages( String baseImageDir,String hardcodedImageName,String imgExt) {
		String sMethod = "handleCheckImages";
		loggerDebugM(sMethod, "start");
		try {			
			
			String imageName = baseImageDir + hardcodedImageName + "." + imgExt.toLowerCase();

			File imgPath = new File(imageName.toString());
			if (!imgPath.exists()) {
				dbgImgNotFound(imgPath);
				return false;
			}

			byte[] bytes = this.extractBytes2(imgPath,imgExt);
			if (bytes == null) {
				loggerDebugM(sMethod, "end-4-null-img-bytes");
				return false;
			}

			logBytes(bytes);			
			
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	public boolean handleImages(CSVRecord record, PersistableProduct product, String baseImageDir,String imgExt) {
		String sMethod = "handleImages";
		loggerDebugM(sMethod, "start");
		try {
			if (!recordIsSetBoolean(record, "image_name")) {
				loggerDebugM(sMethod, "end-2");
				return false;
			}
			String hardcodedImageName = recordGetString(record, "image_name");
			if (StringUtils.isBlank(hardcodedImageName)) {
				loggerDebugM(sMethod, "end-3");
				return false;
			}

			String imageName = baseImageDir + hardcodedImageName + "." + imgExt.toLowerCase();			

			File imgPath = new File(imageName.toString());

			byte[] bytes = this.extractBytes2(imgPath,imgExt);

			if (bytes == null) {
				loggerDebugM(sMethod, "end-4-null-img-bytes");
				return false;
			}

			logBytes(bytes);

			PersistableImage persistableImage = new PersistableImage();
			persistableImage.setBytes(bytes);
			String imgPpathName = imgPath.getName();
			loggerDebugM(sMethod, "imPpathName:" + imgPpathName);
			persistableImage.setName(imgPpathName);

			List<PersistableImage> images = new ArrayList<PersistableImage>();
			images.add(persistableImage);

			product.setImages(images);
			loggerDebugM(sMethod, "end");
		} catch (Exception ex) {
			loggerExceptionM(sMethod, "end", ex);
		}
		loggerDebugM(sMethod, "end");
		return true;
	}

	private void logBytes(byte[] bytes) {
		String sMethod = "logBytes";
		loggerDebugM(sMethod, "start");

		String stx = "";
		for (byte a : bytes) {
			stx += a;
		}
		loggerDebugM(sMethod, "bytes:" + stx);
		loggerDebugM(sMethod, "end");
	}

	private String cleanup(String field) {
		return field.replaceAll("�", "");
	}

	private ProductOptionValue optValue(String code) {
		ProductOptionValue optValue = new ProductOptionValue();
		optValue.setCode(code);
		return optValue;
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
	private void dbgImgNotFound(File imgPath) {
		String sMethod = "extractBytes2";
		loggerDebugM(sMethod, "start");
		
		loggerDebug("--------------------------------------");
		loggerDebug("IMAGE NOT FOUND " + imgPath.getName());
		loggerDebug("IMAGE PATH " + imgPath.getAbsolutePath());
		loggerDebug("--------------------------------------");
		loggerDebugM(sMethod, "return");
	
	}

	public byte[] extractBytes2(File imgPath,String extension) throws Exception {
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

	private List<String> getTokensWithCollection(String str, String token) {
		return Collections.list(new StringTokenizer(str, token)).stream().map(t -> (String) t)
				.collect(Collectors.toList());
	}

	public int minimumQuantity(int quantity) {

		return Integer.parseInt("1");
	}

	private String alternativeIdentifier(CSVRecord record) {
		return recordGetString(record, "sku");
	}

}
