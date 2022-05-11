package com.shopizer.inventory.csv.in.initialize;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

import com.salesmanager.shop.model.catalog.product.type.PersistableProductType;
import com.salesmanager.shop.model.catalog.product.type.ProductTypeDescription;

public class InitializeStore {

	private String endPoint = "http://localhost:8080/api/v1/private/initstore";
	
	private static final String ADMIN_NAME = "admin@shopizer.com";
	private static final String ADMIN_PASSWORD = "password";
	
	private static final String MERCHANT = "DEFAULT";
	
	public static void main(String[] args) {
		
		InitializeStore manufacturerImport = new InitializeStore();
		try {
			manufacturerImport.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void process() throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String id = "1";
		String json = writer.writeValueAsString(id);
		
		System.out.println(json);
		
		HttpHeaders httpHeader = getHeader();			
		
		HttpEntity<String> entity = new HttpEntity<String>(json, httpHeader);
		ResponseEntity response = restTemplate.postForEntity(endPoint + MERCHANT, entity, String.class);
			
		System.out.println("------------------------------------");
		System.out.println("ProductType import done");
		System.out.println("------------------------------------");
		
	}
	
	
	private HttpHeaders getHeader(){
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
		//MediaType.APPLICATION_JSON //for application/json
		headers.setContentType(mediaType);
		//Basic Authentication
		String authorisation = ADMIN_NAME + ":" + ADMIN_PASSWORD;
		byte[] encodedAuthorisation = Base64.encode(authorisation.getBytes());
		headers.add("Authorization", "Basic " + new String(encodedAuthorisation));
		return headers;
	}	

}
