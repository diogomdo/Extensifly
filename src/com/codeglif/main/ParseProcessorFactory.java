package com.codeglif.main;

public class ParseProcessorFactory {

	protected String getName(String formNametoParse){
    	String[] formNamesplitted = formNametoParse.split("\\\\");
    	String formNameParsed = formNamesplitted[formNamesplitted.length-1].toString();
		return formNameParsed.replace(".xfmb", "");
    }
}
