package com.lkd.portal.xml.jaxb.converter.services;

import com.lkd.portal.xml.converter.logging.LkdGenericLogger;

public class MainDispatcher {
	
	LkdGenericLogger logger = new LkdGenericLogger(); 
	
	public void executeHandlers()
	{
		try{
									
		}catch (Exception ex){
			System.out.println("exception:" + ex.getMessage());
			logger.exception("exception:" ,ex);
		}
	}
	

}
