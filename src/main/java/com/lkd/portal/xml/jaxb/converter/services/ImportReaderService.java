package com.lkd.portal.xml.jaxb.converter.services;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jaxb.bc.model.Offers;
import com.lkd.portal.xml.jaxb.converter.config.LkdMainConfig;

public class ImportReaderService {
	LkdMainConfig config = new LkdMainConfig();
	Offers rootXml ;
	
	LkdGenericLogger logger = new LkdGenericLogger();

	private String sRootImportDir = "C:\\lkd\\ht\\apps_jee\\lkd-vm-import\\app\\lkd-vm-import\\src\\main\\resources\\bc\\erp\\data";
	private String sFileData = "C:\\lkd\\ht\\apps_jee\\lkd-vm-import\\app\\lkd-vm-import\\src\\main\\resources\\bc\\erp\\data\\erp_short_for_xsd.xml";
	private String sFileXsd = "C:\\lkd\\ht\\apps_jee\\lkd-vm-import\\app\\lkd-vm-import\\src\\main\\resources\\bc\\erp\\def\\def_erp_stringify.xsd";
	//
	public Offers getRoot(){
		return rootXml;
	}
	
	public String getSourceFilePath()
	{
		return "";
	}
	
	public String setFileFullImport()
	{
		String sFun = "setFileFullImport";
		logger.debugInfo(sFun, "start");
		
		sFileData = sRootImportDir + "\\" + "erp.xml";
		
		logger.debugInfo(sFun, "end");
		return sFileData;
	}
	
	public String getFilemport()
	{
		return sFileData;
	}		
	
	public String setFileTestImport()
	{
		String sFun = "setFileTestImport";
		logger.debugInfo(sFun, "start");
		
		sFileData = sRootImportDir + "\\" + "erp_short_for_xsd.xml";
		logger.debugInfo(sFun, "end");
		return sFileData;
	}
	
	public void executeHandler() throws Exception
	{
		String sFun = "executeHandler";
		logger.debugInfo(sFun, "start");
		
		if(config.getIsTest()){
			setFileTestImport();
		}else{
			setFileFullImport();
		}		
		
		executeHandlerInner();
		
		logger.debugInfo(sFun, "end");
	}
	
	public void executeHandlerInner() throws Exception
	{
		String sFun = "getErpData";
		logger.debugInfo(sFun, "start");
		getErpData();		//
		logger.debugInfo(sFun, "end");        
		return ;
		
	}
	
	
	public void getErpData() throws Exception
	{
		String sFun = "getErpData";
		
		logger.debugInfo(sFun, "start");
		logger.debugInfo(sFun,"reading_xsd::" + sFileXsd);
        File xsdFile = new File(sFileXsd);
        
        logger.debugInfo(sFun,"reading_xml_import::" + sFileData);
        File xmlFile = new File(sFileData);
        
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        logger.debugInfo(sFun,"xsdFile::" + xsdFile.getAbsoluteFile());
        logger.debugInfo(sFun,"xmlFile::" + xmlFile.getAbsoluteFile());
        
        Schema schema = sf.newSchema( xsdFile );
        
        logger.debugInfo(sFun, "schema::" + schema.toString());
        
        String rootPackage = com.lkd.portal.xml.jaxb.bc.model.Offers.class.getPackage().getName();
        logger.debugInfo(sFun,"rootPackage::" + rootPackage);

        JAXBContext context = JAXBContext.newInstance(rootPackage);
        Unmarshaller reader = context.createUnmarshaller();
        reader.setSchema(schema);
        
        logger.debugInfo(sFun,"unmarshal::start");
        this.rootXml = (com.lkd.portal.xml.jaxb.bc.model.Offers) reader.unmarshal( xmlFile );
        logger.debugInfo(sFun,"unmarshal::success");
        
        Integer size = rootXml.getOffer().size();
        
        logger.debugInfo(sFun, "Products::size::" + size );
        logger.debugInfo(sFun, "end");
        
		return ;
		
	}
}

