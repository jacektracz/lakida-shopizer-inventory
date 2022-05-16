package com.lkd.portal.xml.jaxb.converter.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter ;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.lkd.portal.xml.converter.logging.LkdGenericLogger;
import com.lkd.portal.xml.jaxb.bc.model.Offers;
public class ExportWriterService {
	
	
	Offers offersXml ;
	LkdGenericLogger logger = new LkdGenericLogger();
	
	private String sFileOutputData = "C:\\lkd\\ht\\apps_jee\\lkd-vm-import\\app\\lkd-vm-import\\src\\main\\resources\\bc\\erp\\data\\offers_from_erp_2.xml";
	
	public void setOffers(Offers poffers){
		offersXml = poffers;
	}
	
	
	public String setOutputFilePath(String sInput)
	{
		sFileOutputData = sInput + "_output.xml";
		return "";
	}
	
	
	public void executeHandler() throws Exception
	{
		serialiseOutput();
				
	}
	
	public void serialiseOutput() throws Exception
	{
		String sFun = "serialiseOutput";		
		logger.debugInfoLevel0(sFun, "start::" + sFileOutputData);
		
		logger.debugInfo(sFun, "offers_size_to_write::" + offersXml.getOffer().size());
		
		JAXBContext context = JAXBContext.newInstance(Offers.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
         int mode = 2;
         if(mode == 0){
	         File file = new File(sFileOutputData);
	         m.marshal( offersXml, file );
         }
         
         if(mode == 1){         
        	 m.marshal( offersXml, System.out );
         }         
         String result = "";
         if(mode == 2){
        	 StringWriter sw = new StringWriter();
        	 m.marshal(offersXml, sw);
        	 result = sw.toString();
        	 String decoded = decodeString(result, "code");
        	 //printToFile(decoded);
        	 printToFileBuffered( decoded );
         }
         
		logger.debugInfoLevel0(sFun, "end::" + sFileOutputData);
		return ;
	}
	
	public void printToFile(String str){
		String sFun = "printToFile";		
		logger.debugInfo(sFun, "start::" );
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(sFileOutputData))) {
			bw.write(str);
			System.out.println("Done");
		} catch (Exception e) {
			logger.exception( "exception::" ,e);
		}
		logger.debugInfo(sFun, "end::" );
	}

	public void printToFileBuffered(String str){
		String sFun = "printToFile";		
		logger.debugInfo(sFun, "start::" );
		
		try (OutputStreamWriter  bw = new OutputStreamWriter (new FileOutputStream(sFileOutputData),"UTF-8")) {
			bw.write(str);
			System.out.println("Done");
		} catch (Exception e) {
			logger.exception( "exception::" ,e);
		}
		logger.debugInfo(sFun, "end::" );
	}
	
	public String decodeString(String pvalue, String pkey)
	{
		String sFun = "decodeString";				
		String enc = pvalue;
		
		enc = enc.replace("&lt;","<");
		enc = enc.replace("&gt;",">");
		enc = enc.replace("&amp;","&");
		enc = enc.replace("&quot;","\"");
		enc = enc.replace("&apos;","'");
		enc = enc.replace("&nbsp;"," ");
		enc = enc.replace("&ndash;","-");
		enc = enc.replace("&mdash;","--");
		enc = enc.replace("kt&oacute;re","które");
		enc = enc.replace("&oacute;","ó");
		return enc;
	}	
	
}

