package com.codeglif.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class XmlFormReportAnalyser {

	// http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580
	
    private NodeList formsListToParse;
    private Document root;
    private String formName;
    private HashMap<String, FormChanges> formList = new HashMap<>();

    public XmlFormReportAnalyser(Document doc){
        this.root = doc;
    }

    public void startProcess(){
    	
    }
    
    public void formListProcessor(){
    	
//    	NodeList differenceList = root.getElementsByTagName("Difference");
    	NodeList differenceList = root.getChildNodes();
//    	NodeList newList = root.getElementsByTagName("New");

    	Node differenceMainNode = getNode("Differences", differenceList);
    	Node differenceNode = getNode("Difference", differenceMainNode.getChildNodes());

    	getNodeAttr("target", differenceNode);
    	
    	Node newNode = getNode("New", differenceNode.getChildNodes());
    	newNode.getChildNodes().getLength();
    	Node n = getNode("NewOperation", newNode.getChildNodes());
    	NodeList a = n.getChildNodes();
    	
    	//TODO 
    	//create function to get number of elements of node
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
    
    public String getParsedName(String formName){
    	String[] formNamesplitted = formName.split("\\\\");
    	String formNameParsed = formNamesplitted[formNamesplitted.length-1].toString();
		return formNameParsed.replace(".xfmb", "");
    }
}
