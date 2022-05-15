package com.shopizer.inventory.csv.in.product.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductsRequestMapData;

public class ProductRequesEntity2MapMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRequesEntity2MapMapper.class);

	public void getRequestProductsDataFromEntity(ProductsRequestEntityData parser,ProductsRequestMapData dest) {
		
		String sMethod = "getRequestProductsDataFromEntity";
		loggerDebugM(sMethod, "start");

		int ii = 0;
		for (ProductRequestEntityData record : parser.getProductItems()) {						
			loggerDebugM(sMethod, "start-record:" + ii);
			ProductRequestMapData destItem = new ProductRequestMapData();
			getRequestProductDataFromEntity(record, destItem);
			dest.getProductItems().add(destItem);
			loggerDebugM(sMethod, "end-record:" + ii);
			ii++;
		}
		loggerDebugM(sMethod, "end");		
	}
	
	private void getRequestProductDataFromEntity(ProductRequestEntityData parser,ProductRequestMapData dest) {
		
		setDataFromEntityItem(dest,parser.getBarcode(),"barcode");
		setDataFromEntityItem(dest,parser.getCategory(),"category");
		setDataFromEntityItem(dest,parser.getDeal(),"deal");
		setDataFromEntityItem(dest,parser.getDescriptionEn(),"description_en");
		setDataFromEntityItem(dest,parser.getDimension(),"dimension");
		setDataFromEntityItem(dest,parser.getDimensions(),"dimensions");
		setDataFromEntityItem(dest,parser.getImageFile(),"image_file");
		setDataFromEntityItem(dest,parser.getImageName(),"image_name");
		setDataFromEntityItem(dest,parser.getImportStatus(),"import");
		setDataFromEntityItem(dest,parser.getManufacturerCollection(),"collection");
		setDataFromEntityItem(dest,parser.getNameEn(),"name_en");
		setDataFromEntityItem(dest,parser.getPackageHeight(),"package_height");
		setDataFromEntityItem(dest,parser.getPackageLength(),"package_length");
		setDataFromEntityItem(dest,parser.getPackageWeight(),"package_width");
		setDataFromEntityItem(dest,parser.getPosition(),"position");
		setDataFromEntityItem(dest,parser.getPreOrder(),"pre-order");
		setDataFromEntityItem(dest,parser.getPrice(),"price");		
		setDataFromEntityItem(dest,parser.getQuantity(),"quantity");		
		setDataFromEntityItem(dest,parser.getSku(),"sku");				
	}
	
	private String getDbgClassName() {
		return "ProductRequestMapper::";
	}
	
	private boolean setDataFromEntityItem(ProductRequestMapData dest,String recordValue, String key) {
		String sMethod = "recordIsSetBoolean";
		loggerDebugM(sMethod, "start:" + key);
		dest.recordAddString(key, recordValue);
		loggerDebugM(sMethod, "end:" + key + ":" + "true");
		return true;
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
