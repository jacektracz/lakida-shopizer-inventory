package com.lkd.portal.xml.jackson.bc.services;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffers;

public class LkdBcXmlReaderService {
	
	LkdGenericLogger logger = new LkdGenericLogger();

	public  void deserializeFromXML(String fullFilePath) {
	    try {
	        XmlMapper xmlMapper = new XmlMapper();
	        String readContent = new String(Files.readAllBytes(Paths.get(fullFilePath)));

	        LkdBcOffers deserializedData = xmlMapper.readValue(readContent, LkdBcOffers.class);

	    } catch (Exception e) {
	        logger.exception("", e);
	    }
	}
}
