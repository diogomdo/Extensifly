package com.codeglif.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.codeglif.main.diff_match_patch.Diff;
import com.codeglif.main.diff_match_patch.LinesToCharsResult;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils.*;

public class XmlFormReportAnalyser {

	// http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580
	//http://www.javaspecialists.eu/archive/Issue163.html

    private Document root;
    private Integer totalExtForms;
    private HashMap<String, FormChangesFacts> formExtList = new HashMap<>();
    
    private ParseProcessorFactory parseProcessorFactory;

    public XmlFormReportAnalyser(Document doc){
    	this.parseProcessorFactory = new ParseProcessorFactory();
    	this.root = doc;
    }
    
    public void mainReportProcessor(){
    	
    	NodeList differenceList = root.getChildNodes();
    	NodeList differenceMainNode = (NodeList) getNode("Differences", root.getChildNodes());
    	
    	for (int i = 0; i<differenceMainNode.getLength(); i++){
    		Node currentNode = differenceMainNode.item(i);
    		if (currentNode.getNodeName() == "Difference" && currentNode.getNodeType()==Node.ELEMENT_NODE)
    		{
    			formListProcessor(currentNode);
    		}
    	}
    }
    
    public void formListProcessor(Node differenceNode){
    	
    	//TODO
    	//change variable names for something more intuitive
    	//for the navigation between nodes like "onDifferenceNode"
    	String formName = parseProcessorFactory.getName(getNodeAttr("target", differenceNode));

    	formExtList.put(formName,new FormChangesFacts());
    	formExtList.get(formName).setFormName(formName);
    	
    	Node newTagNode = getNode("New", differenceNode.getChildNodes());
    	if (newTagNode != null){
    		formExtList.get(formName).setTotalNewOp(getTotalNewOp(newTagNode));
    	}

    	Node diffTagNode = getNode("Diff", differenceNode.getChildNodes());
    	if (diffTagNode != null){
	    	formExtList.get(formName).setTotalOperationalDiff(getOpperationalDiff(diffTagNode));
//	    	formExtList.get(formName).setTotalStructuralDiff(getStructuralDiff(diffTagNode));
    	}
    	
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
    
    protected Integer getNewOpNodeSize(String tagName, NodeList nodes){
    	int count = 0;
    	for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName) ){
	        	if (parseProcessorFactory.getNewOpValid(node.getAttributes().getNamedItem("name").getNodeValue())){
	        		count +=1;
	        	}
	        }
    	}
    	return count;
	}
    
    protected Integer getTotalNewOp(Node newTagNode){
    	return getNewOpNodeSize("NewOperation", newTagNode.getChildNodes());	
    }
    
    protected Integer getStructuralDiff(Node newTagNode){
    	return getStructuralDiffSize("StructuralDiff", newTagNode.getChildNodes());	
    }
    
    protected Integer getStructuralDiffSize(String tagName, NodeList nodes){
    	int count = 0;
    	for ( int x = 0; x < nodes.getLength(); x++ ) {
    		 Node node = nodes.item(x);
    		 if (node.getNodeName().equalsIgnoreCase(tagName) ){
    			 count +=1;
    		 }
    	}    	
    	return count;
    }
    
    protected Integer getOpperationalDiff(Node newTagNode){
    	return getOpperationalDiffSize("OperationDiff", newTagNode.getChildNodes());
    }
   
    
    protected Integer getOpperationalDiffSize(String tagName, NodeList nodes){
    	
    	int count = 0;
    	int currentNodeSize = 0;
    	
    	currentNodeSize = getNodeSize(tagName, nodes);
    	
    	/*
    	 * TODO
    	 * It is possible to abstract way more
    	 * the node navigation.
    	 * Encapsulate much more code.
    	 * 
    	 * TODO
    	 * How to deal with node <RemovedOperation>
    	 * for now will be ignored
    	 * in the evaluation will discard everyone 
    	 * excpet <OperationDiff>
    	 */
    	
    	for ( int x = 0; x < currentNodeSize; x++ ) {
    		 Node node = nodes.item(x);
    		 if (node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName() == "OperationDiff"){
    			 NodeList diffsNode = (NodeList)node.getChildNodes();
				 
    			 if (getNodeSize("Statement",(NodeList)getNode("File1", diffsNode)) == getNodeSize("Statement",(NodeList)getNode("File2", diffsNode))){
    				 for (int w = 0; w < getNode("File1", diffsNode).getChildNodes().getLength(); w++){
    					 String File1 = "";
    					 String File2 = "";
    					 if ( getNode("File1", diffsNode).getChildNodes().item(w).getNodeType() == Node.ELEMENT_NODE){
    						 File1 = getNode("File1", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
    						 File2 = getNode("File2", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
    					 }
    					 if(!File1.isEmpty() && !File2.isEmpty()){
    						 if(statementsCompare(File1, File2)){
    							 count +=1;
    						 }
    					 } 
    				 }
	    		 }else{
	    			 /*
	    			  * TODO
	    			  * This condition needs to be worked.
	    			  * Encapsulate all the new operation types and
	    			  * further analysis is needed.
	    			  * There are three situations possible, they are noted.
	    			  */
	    			 count +=1;
	    		 }
    		 }
    	}
    	return count;
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
