package com.shopizer.inventory.entity.in.product.services;

import java.nio.charset.Charset;

import org.apache.commons.csv.CSVRecord;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

import com.salesmanager.shop.model.catalog.product.PersistableProduct;
import com.shopizer.inventory.csv.in.product.model.ProductRequestEntityData;
import com.shopizer.inventory.csv.in.product.model.ProductRequestMapData;



public class ProductImportByEntityController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImportByEntityController.class);
	
	public boolean debugRecord(CSVRecord record,PersistableProduct product,int ii) {
		String sMethod = "debugRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = writer.writeValueAsString(product);
			
			loggerDebug("Line " + ii + " ********************");
			loggerDebug(json);
			
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}
	
	public boolean sendRecord(ProductRequestEntityData record ,PersistableProduct product,int ii,boolean dryRun,String uri) {
		String sMethod = "sendRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = writer.writeValueAsString(product);
			
			loggerDebug("Line " + ii + " ********************");
			loggerDebug(json);

			//post to create category v0 API web service

			if(!dryRun) {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders httpHeader = getHeader(dryRun);
				HttpEntity<String> entity = new HttpEntity<String>(json, httpHeader);
				ResponseEntity<PersistableProduct> response = restTemplate.postForEntity(uri, entity, PersistableProduct.class);
				PersistableProduct prod = (PersistableProduct) response.getBody();
				loggerDebugM(sMethod,"PRODUCT:" + prod.toString());
			}
			
			return true;
		}catch(Exception ex) {
			loggerExceptionM(sMethod, "end",ex);
			return false;
		}
	}		
    
	private HttpHeaders getHeader(boolean dryRun){
		if (dryRun) return null;
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
		//MediaType.APPLICATION_JSON //for application/json
		headers.setContentType(mediaType);
		//Basic Authentication
		String authorisation = "admin@shopizer.com" + ":" + "password";
		byte[] encodedAuthorisation = Base64.encode(authorisation.getBytes());
		headers.add("Authorization", "Basic " + new String(encodedAuthorisation));
		return headers;
	}
	
	private String getDbgClassName() {
		return "ProductFileManagerImpl::";
	}

	private void loggerDebug(String ttx) {
		String stx = getDbgClassName() + ttx;
		LOGGER.debug(stx);
	}
	
	private void loggerDebugM(String sMethod, String ttx) {
		String stx = getDbgClassName()+ ":" + sMethod + ":" + ttx;
		LOGGER.debug(stx);
	}
	
	private void loggerExceptionM(String sMethod, String ttx,Exception ex) {
		String stx = getDbgClassName()+ ":" + sMethod + ":" + ttx;
		LOGGER.debug(stx);
		LOGGER.error(ex.getMessage());
	}
	
}
