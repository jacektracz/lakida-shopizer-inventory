package com.lkd.portal.xml.jackson.bc.services;

import java.io.File;
import java.io.FileWriter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffers;

public class LkdBcXmlWriterService {
	
	LkdGenericLogger logger = new LkdGenericLogger();

	public void serializeToXML(LkdBcOffers dataItem,String fullFilePath) {
	    try {
	        XmlMapper xmlMapper = new XmlMapper();
	        String xmlString = xmlMapper.writeValueAsString(dataItem);

	        File xmlOutput = new File(fullFilePath);
	        FileWriter fileWriter = new FileWriter(xmlOutput);
	        fileWriter.write(xmlString);
	        fileWriter.close();
	    } catch (Exception ex) {
	        logger.exception("", ex);
	    } 
	}

}
