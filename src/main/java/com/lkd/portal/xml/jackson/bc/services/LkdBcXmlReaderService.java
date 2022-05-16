package com.lkd.portal.xml.jackson.bc.services;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jackson.bc.model.LkdBcOffers;

public class LkdBcXmlReaderService {

	LkdGenericLogger logger = new LkdGenericLogger();

	public LkdBcOffers deserializeFromXML(String fullFilePath) {
		String sMethod = "deserializeFromXML";
		loggerDebugM(sMethod, "start");
		try {
			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			String readContent = new String(Files.readAllBytes(Paths.get(fullFilePath)));
			loggerDebugM(sMethod, "content:" + readContent);
			LkdBcOffers deserializedData = xmlMapper.readValue(readContent, LkdBcOffers.class);
			
			XmlMapper xmlMapperTest = new XmlMapper();
			xmlMapperTest.enable(SerializationFeature.INDENT_OUTPUT);
			String xmlString = xmlMapperTest.writeValueAsString(deserializedData);
			loggerDebugM(sMethod, "content_deserialized:" + xmlString);
			
			loggerDebugM(sMethod, "end");
			return deserializedData;
		} catch (Exception e) {
			logger.exception("", e);
			return null;
		}
	}

	private String getDbgClassName() {
		return "LkdBcXmlReaderService::";
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
