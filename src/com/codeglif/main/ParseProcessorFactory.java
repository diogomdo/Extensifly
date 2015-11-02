package com.codeglif.main;

public class ParseProcessorFactory {

	protected String getName(String formNametoParse){
    	String[] formNamesplitted = formNametoParse.split("\\\\");
    	String formNameParsed = formNamesplitted[formNamesplitted.length-1].toString();
		return formNameParsed.replace(".xfmb", "");
    }
	
	protected Boolean getNewOpValid(String nodeAttr){
		if ((nodeAttr.contains("$") && nodeAttr.contains("CLASS")) || nodeAttr.contains("AUDIT")){
			return false;
		}else{
			return true;
		}
	}
}
