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
	//http://www.javaspecialists.eu/archive/Issue163.html

    private Document root;
    private Integer totalExtForms;
    private HashMap<String, FormChangesFacts> formExtList = new HashMap<>();

    public XmlFormReportAnalyser(Document doc){
        this.root = doc;
    }
    
    public void mainReportProcessor(){
    	
    	NodeList differenceList = root.getChildNodes();
    	NodeList differenceMainNode = (NodeList) getNode("Differences", root.getChildNodes());
//    	totalExtForms = getNodeSize("Difference",differenceMainNode.getChildNodes());
//    	NodeList DifferenceNodeList = (NodeList) getNode("Difference",differenceMainNode.getChildNodes());
//    	System.out.println("nr forms to extend: " +totalExtForms);
    	
    	for (int i = 0; i<differenceMainNode.getLength(); i++){
    		Node currentNode = differenceMainNode.item(i);
    		if (currentNode.getNodeName() == "Difference")
    		{
    			formListProcessor(currentNode);
    		}
    	}
    	
//    	formListProcessor(differenceMainNode);
    }
    
    public void formListProcessor(Node differenceMainNode){
    	
    	//TODO
    	//change variable names for something more intuitive
    	//for the navigation beetween nodes like "onDifferenceNode"
    	
//    	NodeList differenceList = root.getChildNodes();
//    	Node differenceMainNode = getNode("Differences", differenceList);
    	
    	Node differenceNode = getNode("Difference", differenceMainNode.getChildNodes());
    	
    	ParseProcessorFactory parseProcessorFactory = new ParseProcessorFactory();
    	String formName = parseProcessorFactory.getName(getNodeAttr("target", differenceNode));
    	
    	formExtList.put(formName,new FormChangesFacts(formName));
    	
    	Node newTagNode = getNode("New", differenceNode.getChildNodes());
    	formExtList.get(formName).setTotalNewOp(getNodeSize("NewOperation", newTagNode.getChildNodes()));
    	
    	//Node newTagNodeTagNode = getNode("NewOperation", newTagNode.getChildNodes());
    	Node newOperationDiffTagNode = getNode("Diff", differenceNode.getChildNodes());
    	formExtList.get(formName).setTotalOperationalDiff(getNodeSize("OperationDiff",newOperationDiffTagNode.getChildNodes()));
    	formExtList.get(formName).setTotalStructuralDiff(getNodeSize("StructuralDiff",newOperationDiffTagNode.getChildNodes()));
    	
    	formExtList.get(formName).getAllFormFacts();
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
    
    
}
