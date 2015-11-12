package com.codeglif.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.BlockView;

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
    private String formName;
    private HashMap<String, FormChangesFacts> formExtList = new HashMap<>();
    private Node mainFormNode;
    static final Set<String> structuralTypes = new HashSet<String>(Arrays.asList("LOV","Canvas","Block_Item"));
    
    
    private Utilities util;

    public XmlFormReportAnalyser(Document doc){
    	this.util = new Utilities();
    	this.root = doc;

    }
    
    public void mainReportProcessor(){
    	
//    	NodeList differenceList = root.getChildNodes();
    	NodeList differenceMainNode = (NodeList) util.getNode("Differences", root.getChildNodes());
    	
    	for (int i = 0; i<differenceMainNode.getLength(); i++){
    		mainFormNode = differenceMainNode.item(i);
    		if (mainFormNode.getNodeName() == "Difference" && mainFormNode.getNodeType()==Node.ELEMENT_NODE)
    		{
    			formListProcessor(mainFormNode);
    		}
    	}
    }
    
    public void formListProcessor(Node differenceNode){
    	
    	//TODO
    	//change variable names for something more intuitive
    	//for the navigation between nodes like "onDifferenceNode"
    	formName = util.getName(util.getNodeAttr("target", differenceNode));

    	formExtList.put(formName,new FormChangesFacts());
    	formExtList.get(formName).setFormName(formName);
    	
    	Node newTagNode = util.getNode("New", differenceNode.getChildNodes());
    	if (newTagNode != null){
    		formExtList.get(formName).setTotalNewOp(getTotalNewOp(newTagNode));
    	}

    	Node diffTagNode = util.getNode("Diff", differenceNode.getChildNodes());
    	if (diffTagNode != null){
	    	formExtList.get(formName).setTotalOperationalDiff(getOpperationalDiff(diffTagNode));
	    	getStructuralDiff(diffTagNode);
    	}
    	
    	formExtList.get(formName).getAllFormFacts();
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
	        	if (util.getNewOpValid(node.getAttributes().getNamedItem("name").getNodeValue())){
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
    
    protected void getStructuralDiff(Node newTagNode){
    	getStructuralDiffSize("StructuralDiff", newTagNode.getChildNodes());	

    }
        
    protected void getStructuralDiffSize(String tagName, NodeList nodes){
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
    			 
    			 if (nodeName.equals("LOV")){
    				 int TotalNewLov = isLovCountable(diffsNode);
    				 formExtList.get(formName).setTotalNewLov(TotalNewLov);
    			 }
    			 else if ( nodeName.equals("Canvas")){
    				 int totalNewCanvas = isCanvasCountable(diffsNode);
    				 formExtList.get(formName).setTotalNewCanvas(totalNewCanvas);
    			 	}
    			 else if (nodeName.equals("Block_Item") ){
    				 isItemPropertyCountable(currentNode);
    				 isBlockCountable(currentNode);
    			 }
			 }
		 }
    	}
    
      private void isBlockCountable(Node currentNode) {
    	String blockName;
    	Integer count=0;
    	
		if (util.getNodeSize("Element",util.getNode("File2",(NodeList)currentNode).getChildNodes()) > 0)
			for (int x = 0; x < util.getNode("File2",(NodeList)currentNode).getChildNodes().getLength() ; x++){
				if (util.getNode("File2",(NodeList)currentNode).getChildNodes().item(x).getNodeName() == "Element" &&
					  util.getNode("File2",(NodeList)currentNode).getChildNodes().item(x).getAttributes().getNamedItem("diffType").getNodeValue().equals("Missing") &&
					  util.getNode("File2",(NodeList)currentNode).getChildNodes().item(x).getAttributes().getNamedItem("node").getNodeValue().equals("Block")){
						formExtList.get(formName).setTotalNewBlock(count+=1);
						blockName = util.getNode("File2",(NodeList)currentNode).getChildNodes().item(x).getAttributes().getNamedItem("value").getNodeValue();
						formExtList.get(formName).setTotalNewItems(newBlockItems(currentNode, blockName));
					}
			}
	}

	private Integer newBlockItems(Node currentNode, String blockName) {

		for (int x = 0; x < util.getNodeSize("NewOperation",(NodeList)util.getNode("New",mainFormNode.getChildNodes()).getChildNodes()) ; x++){
			if (util.getNode("New",mainFormNode.getChildNodes()).getChildNodes().item(x).getNodeName() == "NewOperation"){
				String newOpName = util.getNode("New",mainFormNode.getChildNodes()).getChildNodes().item(x).getAttributes().getNamedItem("name").getNodeValue();
				
			}
		}
		return null;
	}

	private Integer isLovCountable(NodeList nodeItem){
    	  
    	  int count = 0;
    	  /*
    	   * Condition eliminated elements
    	   */
    	  if (util.getNodeSize("Element",util.getNode("File1",(NodeList)nodeItem).getChildNodes()) > 0  ){
    		  for (int x = 0; x < util.getNode("File1",(NodeList)nodeItem).getChildNodes().getLength() ; x++){
				  if (util.getNode("File1",(NodeList)nodeItem).getChildNodes().item(x).getNodeName() == "Element" &&
						  util.getNode("File2",(NodeList)nodeItem).getChildNodes().item(x).getAttributes().getNamedItem("diffType").getNodeValue().equals("Missing")){
					  count += 1;
				  }
    		  }
    	  }
    	  /*
    	   * Condition for new elements added or value change
    	   */
    	  if (util.getNodeSize("Element",util.getNode("File2",(NodeList)nodeItem).getChildNodes()) > 0  ){
			  for (int x = 0; x < util.getNode("File2",(NodeList)nodeItem).getChildNodes().getLength() ; x++){
				  if (util.getNode("File2",(NodeList)nodeItem).getChildNodes().item(x).getNodeName() == "Element"){
					  count += 1;
				  }
			  }
		  
    	  }
    	  return count;
    }
    private void isItemPropertyCountable(Node nodeItem){
 
    	int count = 0;
	   	  if (util.getNode("Element",util.getNode("File1",(NodeList)nodeItem).getChildNodes()) == null 
	   			  && util.getNodeSize("Element",util.getNode("File2",(NodeList)nodeItem).getChildNodes()) > 0  ){
			  
				  for (int x = 0; x < util.getNode("File2",(NodeList)nodeItem).getChildNodes().getLength() ; x++){
					  
					  if (util.getNode("File2",(NodeList)nodeItem).getChildNodes().item(x).getNodeName() == "Element" &&
							  util.getNode("File2",(NodeList)nodeItem).getChildNodes().item(x).getAttributes().getNamedItem("diffType").getNodeValue().equals("Missing")){
						  count += 1;
					  }
				  }
			  
	   	  }
    }
  
    private int isCanvasCountable(NodeList diffsNode){
	int count = 0;
	if (util.getNodeSize("Element",util.getNode("File2",(NodeList)diffsNode).getChildNodes()) > 0  ){
	  
	  for (int x = 0; x < util.getNode("File2",(NodeList)diffsNode).getChildNodes().getLength() ; x++){
	  
		  if (util.getNode("File2",(NodeList)diffsNode).getChildNodes().item(x).getNodeName() == "Element" &&
				  util.getNode("File2",(NodeList)diffsNode).getChildNodes().item(x).getAttributes().getNamedItem("diffType").getNodeValue().equals("Missing") &&
				  util.getNode("File2",(NodeList)diffsNode).getChildNodes().item(x).getAttributes().getNamedItem("node").getNodeValue().equals("Canvas")){
				  count += 1;
			  }
	  	}
		  
  	  }
  	  return count;
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
				 
    			 if (util.getNodeSize("Statement",(NodeList)util.getNode("File1", diffsNode)) == util.getNodeSize("Statement",(NodeList)util.getNode("File2", diffsNode))){
    				 for (int w = 0; w < util.getNode("File1", diffsNode).getChildNodes().getLength(); w++){
    					 this.file1 = "";
    					 this.file2 = "";
    					 if (util.getNode("File1", diffsNode).getChildNodes().item(w).getNodeType() == Node.ELEMENT_NODE){
    						 this.file1 = util.getNode("File1", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
    						 this.file2 = util.getNode("File2", diffsNode).getChildNodes().item(w).getAttributes().getNamedItem("stmt").toString().replace("\n", "").replace(" ", "");
    					 }
    					 if(!file1.isEmpty() && !file2.isEmpty()){
    						 if(util.statementsCompare(file1, file2)){
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
}
