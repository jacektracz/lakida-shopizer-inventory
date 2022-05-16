/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lkd.portal.xml.converter.logging;

import com.lkd.portal.xml.jaxb.converter.config.LkdMainConfig;

//import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;

/**
 *
 * @author USER
 */

public class LkdGenericLogger {
	
	int current_level = 0;
	
	public LkdGenericLogger(){
		current_level = 0;
	}
	public void debug(String sLogline){
		if(0 >= current_level ){		
			System.out.println(sLogline);
		}
	}
	
	public void debugInfoLevel0(String sFun,String sLogline){
		if(0 >= current_level ){				
			System.out.println(sFun + "::" +sLogline);
		}
	}
	
	public void debugInfoLevel20(String sFun,String sLogline){
		if(20 >= current_level ){
			System.out.println(sFun + "::" +sLogline);
		}
	}
	public void debugInfoLevel10(String sFun,String sLogline){
		if(10 >= current_level ){
			System.out.println(sFun + "::" +sLogline);
		}
	}
	
	public void debugInfo(String sFun,String sLogline){
		if(0 >= current_level ){
			System.out.println(sFun + "::" +sLogline);
		}
	}
	
	public void exception(String sLogline, Exception ex){
		printRawException(sLogline, ex);
		//if ( ex instanceof IllegalAnnotationException){
			//IllegalAnnotationException exi = (IllegalAnnotationException)ex;
			//printRawException(sLogline, exi);
		//}
	}
	
	public void printRawException(String sLogline, Exception ex){
		System.out.println(sLogline);
		System.out.println(ex.getMessage());
		System.out.println(ex.getStackTrace());
		System.out.println(ex.getCause());
	}
	
}

