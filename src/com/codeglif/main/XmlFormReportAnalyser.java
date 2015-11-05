package com.codeglif.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlFormReportAnalyser {

	// http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580
	//http://www.javaspecialists.eu/archive/Issue163.html

    private Document root;
    private String file1;
    private String file2;
    private HashMap<String, FormChangesFacts> formExtList = new HashMap<>();
    
    static final Set<String> structuralTypes = new HashSet<String>(Arrays.asList("LOV","Canvas","Block_Item"));
    
    
    private ParseProcessorFactory parseProcessorFactory;

    public XmlFormReportAnalyser(Document doc){
    	this.parseProcessorFactory = new ParseProcessorFactory();
    	this.root = doc;
    	this.file1 = file1;
    	this.file2 = file2;
    }
    
    public void mainReportProcessor(){
    	
//    	NodeList differenceList = root.getChildNodes();
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
	    	formExtList.get(formName).setTotalStructuralDiff(getStructuralDiff(diffTagNode));
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
    
    /*
     * Node - <NewOperation>
     * get totals of new operations with necessary exceptions
     */
    
    protected Integer getNewOpNodeSize(String tagName, NodeList nodes){
    	int count = 0;
    	
    	/*
         * TODO
         * this class is obsolete. It is possible
         * to reduce the code lines for the simple
         * task
         */
        
        
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
    
    /*
     * NODE - <Structural>
     * Deals with Structural differences
     */
    
    protected Integer getStructuralDiff(Node newTagNode){
    	return getStructuralDiffSize("StructuralDiff", newTagNode.getChildNodes());	
    }
        
    protected Integer getStructuralDiffSize(String tagName, NodeList nodes){
    	int count = 0;
    	/*
    	 * TODO
    	 * add rules for each node name
    	 * like Lov, Block Item
    	 * 
    	 */
    	
    	for ( int x = 0; x < nodes.getLength(); x++ ) {
    		 Node currentNode = nodes.item(x);
    		 if (currentNode.getNodeName() == tagName && currentNode.getNodeType() == Node.ELEMENT_NODE){
    			 NodeList diffsNode = (NodeList)currentNode.getChildNodes();
    			 String nodeName = currentNode.getAttributes().getNamedItem("name").getNodeValue();
    			 getFilesDiff(diffsNode, nodeName);
     			
    			 if (nodeName =="Block_Item"){
    				 
    			 	 }
    			 }
    		 }
    	return count;
    	}
    
    private Boolean isBlockItemCountable(Node nodeItem){
 
    	return true;
    }
    private Boolean isLovCountable(Node nodeItem){
    	 
    	return true;
    }
    private Boolean isCanvasCountable(Node nodeItem){
    	 
    	return true;
    }
    
    

    /*
     * NODE - <Operational>
     * Deals with Operational differences
     */
    
    protected Integer getOpperationalDiff(Node newTagNode){
    	return getOpperationalDiffSize("OperationDiff", newTagNode.getChildNodes());
    }
   
    
    protected Integer getOpperationalDiffSize(String tagName, NodeList nodes){
    	
    	int count = 0;
    	
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
    	 * except <OperationDiff>
    	 */
    	
    	for ( int x = 0; x < nodes.getLength(); x++ ) {
    		 Node node = nodes.item(x);
    		 if (node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName() == tagName){
    			 NodeList diffsNode = (NodeList)node.getChildNodes();
				 
    			 if (getNodeSize("Statement",(NodeList)getNode("File1", diffsNode)) == getNodeSize("Statement",(NodeList)getNode("File2", diffsNode))){
    				 for (int w = 0; w < getNode("File1", diffsNode).getChildNodes().getLength(); w++){
    					 this.file1 = "";
    					 this.file2 = "";
    					 if ( getNode("File1", diffsNode).getChildNodes().item(w).getNodeType() == Node.ELEMENT_NODE){
    						 this.file1 = getNode("File1", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
    						 this.file2 = getNode("File2", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
    					 }
    					 if(!file1.isEmpty() && !file2.isEmpty()){
    						 if(parseProcessorFactory.statementsCompare(file1, file2)){
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
    
    /*
     * Utilities methods
     */
    
    protected void getFilesDiff(NodeList diffsNode, String nodeName){
    
		for (int w = 0; w < getNode("File1", diffsNode).getChildNodes().getLength(); w++){
			this.file1 = "";
			this.file2 = "";
			 if ( getNode("File1", diffsNode).getChildNodes().item(w).getNodeType() == Node.ELEMENT_NODE){
				 this.file1 = getNode("File1", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
				 this.file2 = getNode("File2", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
			 }
		}
			
    }
	
}
