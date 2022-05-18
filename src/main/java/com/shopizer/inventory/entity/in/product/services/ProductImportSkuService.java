package com.shopizer.inventory.entity.in.product.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ProductImportSkuService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportSkuService.class);
	
	private Map<String,Integer> skuMap = new HashMap<String,Integer>();
		
	public String uniqueSku(String sku,int iterationCount) {
		sku = sku.replace("-", "");
		sku = sku.replace(" ", "");
		if(skuMap.containsKey(sku)) {
			int count = skuMap.get(sku);
			count = count ++;
			sku = count+ sku;
		} else {
			skuMap.put(sku, iterationCount);
		}
		
		return sku.trim();
	}

	
	/**
	 * Can be used to generate a sku on the fly
	 * @return
	 */
	private String generateSku() {
		String sku = UUID.randomUUID().toString();
		sku = sku.replace("-", "");
		return sku;
	}
}
