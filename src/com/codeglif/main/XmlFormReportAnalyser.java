package com.codeglif.main;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by diogo on 19-10-2015.
 */
public class XmlFormReportAnalyser {


    private NodeList formsListToParse;
    private HashMap<String, String> formList = new HashMap<>();

    public XmlFormReportAnalyser(NodeList formsListToParse){
        this.formsListToParse = formsListToParse;

    }

    public void formListProcessor(){

        for(int temp = 0;temp < formsListToParse.getLength();temp++) {

            Node nNode = this.formsListToParse.item(temp);
            System.out.println("Node name: " + nNode.getNodeName());

            NamedNodeMap attributes = nNode.getAttributes();

            for (int a = 0; a < attributes.getLength(); a++) {
                Node theAttribute = attributes.item(a);
                System.out.println(theAttribute.getNodeName() + "=" + theAttribute.getNodeValue());
                formList.put(theAttribute.getNodeValue(),"");
            }
        }
        System.out.println(formList);
    }
}
