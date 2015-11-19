package com.codeglif.main;

import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class ExtensifyMain {
    public static void main(String argv[]){
    	
    	ArrayList<EvalSpecs> listOfEvalSpecs = new ArrayList<>();
        
    	try{

            File diffsXML = new File("./ReportSample/ExtensibilityInspector.finance_sc_xfmb.20150930101230.diffs.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(diffsXML);

            doc.getDocumentElement().normalize();
            
            XmlFormReportAnalyser formProcessing = new XmlFormReportAnalyser(doc);
            formProcessing.mainReportProcessor();
            
            for (ChangeFacts currentExt : formProcessing.getExtensionList()){
            	EvalSpecs currentExtEval = new EvalSpecs(currentExt);
            	currentExtEval.setExtensionName(currentExt.getFormName()); ;
            	listOfEvalSpecs.add(currentExtEval);
            	currentExtEval.print();
            }
            
            WriteTable export = new WriteTable(formProcessing.getExtensionList());
            export.write();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
