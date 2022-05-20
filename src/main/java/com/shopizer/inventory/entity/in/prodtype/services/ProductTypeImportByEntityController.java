package com.shopizer.inventory.entity.in.prodtype.services;

import java.nio.charset.Charset;

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

import com.salesmanager.shop.model.catalog.product.type.PersistableProductType;
import com.shopizer.inventory.entity.in.prodtype.model.ProductTypeRequestEntityData;



public class ProductTypeImportByEntityController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeImportByEntityController.class);
	
	public boolean sendRecord(ProductTypeRequestEntityData record ,PersistableProductType itemToSend,int ii,boolean dryRun,String uri) {
		String sMethod = "sendRecord";
		loggerDebugM(sMethod, "start");
		try {
			
			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = writer.writeValueAsString(itemToSend);
			
			loggerDebug("Line " + ii + " ********************");
			loggerDebug(json);

			//post to create category v0 API web service

			if(!dryRun) {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders httpHeader = getHeader(dryRun);
				HttpEntity<String> entity = new HttpEntity<String>(json, httpHeader);
				ResponseEntity<PersistableProductType> response = restTemplate.postForEntity(uri, entity, PersistableProductType.class);
				PersistableProductType prod = (PersistableProductType) response.getBody();
				loggerDebugM(sMethod,"Manufacturer:" + prod.toString());
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
