package com.codeglif.main;

import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ExtensifyMain {
    public static void main(String argv[]){

        try{

            File diffsXML = new File("./ReportSample/ExtensibilityInspector.finance_sc_xfmb.20150930101230.diffs.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(diffsXML);

            doc.getDocumentElement().normalize();
            
            XmlFormReportAnalyser formProcessing = new XmlFormReportAnalyser(doc);
            formProcessing.formListProcessor();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
