package com.codeglif.main;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class XmlFormReportAnalyser {


    private NodeList formsListToParse;
    private String formName;
    private HashMap<String, FormChanges> formList = new HashMap<>();

    public XmlFormReportAnalyser(NodeList formsListToParse){
        this.formsListToParse = formsListToParse;

    }

    public void formListProcessor(){

        for(int temp = 0;temp < formsListToParse.getLength();temp++) {
        	
            Node nNode = this.formsListToParse.item(temp);
            NamedNodeMap attributes = nNode.getAttributes();

            for (int a = 0; a < attributes.getLength(); a++) {
                Node theAttribute = attributes.item(a);
                if (theAttribute.getNodeName() == "target")
                {
                	formName = getParsedName(theAttribute.getNodeValue());
                	System.out.println(theAttribute.getNodeName() + "=" + getParsedName(theAttribute.getNodeValue()));
                }                
            }
            formList.put(formName, new FormChanges());
        }
        
        System.out.println(formList);

    }
    
    public String getParsedName(String formName){
    	String[] formNamesplitted = formName.split("\\\\");
    	String formNameParsed = formNamesplitted[formNamesplitted.length-1].toString();
		return formNameParsed.replace(".xfmb", "");
    }
}
