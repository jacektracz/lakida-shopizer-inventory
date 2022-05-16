package com.lkd.portal.xml.jackson.bc.services;

import java.io.File;
import java.io.FileWriter;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffers;

public class LkdBcXmlWriterService {
	
	LkdGenericLogger logger = new LkdGenericLogger();

	public void serializeToXML(LkdBcOffers dataItem,String fullFilePath) {
		String sMethod = "serializeToXML";
		loggerDebugM(sMethod, "start");
		
	    try {
	        DefaultPrettyPrinter printer = new DefaultPrettyPrinter()
	                .withObjectIndenter(new DefaultIndenter("  ", "\n"));	    	
	        XmlMapper xmlMapper = new XmlMapper();
	        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
	        String xmlString = xmlMapper.writeValueAsString(dataItem);

	        File xmlOutput = new File(fullFilePath);
	        FileWriter fileWriter = new FileWriter(xmlOutput);
	        fileWriter.write(xmlString);
	        fileWriter.close();
	    } catch (Exception ex) {
	        logger.exception("", ex);
	    } 
	}
	private String getDbgClassName() {
		return "LkdBcXmlWriterService::";
	}

	private void loggerDebug(String ttx) {
		String stx = getDbgClassName() + ttx;
		logger.debug(stx);
	}

	private void loggerDebugM(String sMethod, String ttx) {
		String stx = getDbgClassName() + ":" + sMethod + ":" + ttx;
		logger.debug(stx);
	}

	private void loggerExceptionM(String sMethod, String ttx, Exception ex) {
		String stx = getDbgClassName() + ":" + sMethod + ":" + ttx;
		logger.debug(stx);
		logger.exception(stx, ex);
	}

}
