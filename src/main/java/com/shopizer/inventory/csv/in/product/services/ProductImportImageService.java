package com.shopizer.inventory.csv.in.product.services;

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

public class ProductImportImageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportImageService.class);
	
	public boolean handleImages(ProductRequestMapData record, PersistableProduct product, String baseImageDir,String imgExt) {
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
			String shortName = hardcodedImageName + "." + imgExt.toLowerCase();
			String imagePath = baseImageDir + hardcodedImageName + "." + imgExt.toLowerCase();			

			File imgPath = new File(imagePath.toString());

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
			persistableImage.setName(shortName);
			persistableImage.setDefaultImage(true);
			loggerDebugM(sMethod, "imagePath:" + imagePath);
			persistableImage.setPath(imagePath);
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
	private void dbgImgNotFound(File imgPath) {
		String sMethod = "extractBytes2";
		loggerDebugM(sMethod, "start");
		
		loggerDebug("--------------------------------------");
		loggerDebug("IMAGE NOT FOUND " + imgPath.getName());
		loggerDebug("IMAGE PATH " + imgPath.getAbsolutePath());
		loggerDebug("--------------------------------------");
		loggerDebugM(sMethod, "return");
	
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
