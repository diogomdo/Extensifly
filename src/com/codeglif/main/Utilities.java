package com.codeglif.main;

import java.util.LinkedList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.codeglif.main.diff_match_patch.Diff;

public class Utilities {

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
	
	 protected String getNodeAttr(String attrName, Node node ) {
	        NamedNodeMap attrs = node.getAttributes();
	        for (int y = 0; y < attrs.getLength(); y++ ) {
	            Node attr = attrs.item(y);
	            if (attr.getNodeName().equalsIgnoreCase(attrName)) {
	                return attr.getNodeValue();
	            }
	        }
	        return "";
	    }

	    protected Node getNode(String tagName, NodeList nodes) {
	        for ( int x = 0; x < nodes.getLength(); x++ ) {
	            Node node = nodes.item(x);
	            if (node.getNodeName().equalsIgnoreCase(tagName)) {
	                return node;
	            }
	        }
	     
	        return null;
	    }
	    
	    protected Integer getNodeSize(String tagName, NodeList nodes){
	    	int count = 0;
	    	for ( int x = 0; x < nodes.getLength(); x++ ) {
		        Node node = nodes.item(x);
		        if (node.getNodeName().equalsIgnoreCase(tagName)) {
		        	count +=1;
		        }
	    	}
	    	return count;
		}
	    
	    protected Boolean newBlockItem (String newOpName, String blockName){

	    	String[] parsedValue = newOpName.split(".");
	    	String blockNameParsed = parsedValue[1];
	    	if (blockName.equals(blockNameParsed)){
	    		return true;
	    	}else{
		    	return false;
	    	}
	    }
}
