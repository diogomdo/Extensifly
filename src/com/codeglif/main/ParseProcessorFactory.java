package com.codeglif.main;

import java.util.LinkedList;

import com.codeglif.main.diff_match_patch.Diff;

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
	
	protected Boolean statementsCompare(String lineFile1, String lineFile2) {
		
		String eval = "";
		Boolean diffIsNumb = false;
		
		diff_match_patch dmp = new diff_match_patch();  
		LinkedList<Diff> dmplinesResult = dmp. diff_main(lineFile1, lineFile2);  
		for(int x = 0; x < dmplinesResult.size(); x++){
			if(dmplinesResult.get(x).operation.name().equals("DELETE")){
				eval = dmplinesResult.get(x).text.toString();
				diffIsNumb = org.apache.commons.lang3.StringUtils.isNumeric(eval);
				break;
			}
		}
			
		if (diffIsNumb || eval.length() <= 4){
			return false;
		}else{
		  return true;
		}   
	}
}
