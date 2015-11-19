/**
 * 
 */
package com.codeglif.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import com.codeglif.main.EvalSpecs.EvalFacts;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/*
 * http://www.vogella.com/tutorials/JavaExcel/article.html
 * http://stackoverflow.com/questions/150646/how-do-i-create-a-new-excel-file-using-jxl
 * http://wiki.scn.sap.com/wiki/display/XI/CODE+-+Create+an+Excel+File+via+the+JExcel+API
 * 
 */
public class WriteTable {
	
	private String fileName = "./Export/file.xls";
	private ArrayList<ChangeFacts> cFacts;
	WritableSheet extensionReportSheet = null;
	private Utilities util;
	
	public WriteTable(ArrayList<ChangeFacts> changeFacts){
		this.util = new Utilities();
		this.cFacts = changeFacts;
	}
	
	public void write() throws IOException, WriteException {
	    File file = new File(fileName);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    WritableSheet extensionReportSheet = workbook.createSheet("Extension Report", 0);
	    setTableHeaders(extensionReportSheet);
//	    contentFiller(cFacts, extensionReportSheet);
	    workbook.write();
	    workbook.close();
	  }
	
	public void setTableHeaders(WritableSheet reportSheet) throws WriteException, WriteException{
		EvalFacts[] ExtFactsNames = EvalSpecs.EvalFacts.values();
		ArrayList<String> listOfItemsHeaders = new ArrayList<>();
		 for (int i = 0; i < ExtFactsNames.length; i++){
			 listOfItemsHeaders.add(ExtFactsNames[i].name());
		 }
		 listOfItemsHeaders.add(0, "NAME");
		 listOfItemsHeaders.add(listOfItemsHeaders.size(), "DIFF REL");
		 listOfItemsHeaders.add(listOfItemsHeaders.size(), "DIFF ABS");
		 listOfItemsHeaders.add(listOfItemsHeaders.size(), "TIME");
		 
		 for (int i = 0; i < listOfItemsHeaders.size(); i++) {
             Label label = new Label(i, 0, listOfItemsHeaders.get(i));
             reportSheet.addCell(label);
             reportSheet.getWritableCell(i, 0);
         }
	}
	
	public void contentFiller(ArrayList<ChangeFacts> cFacts, WritableSheet extensionReportSheet,ArrayList<String> listOfItemsHeaders){
		
		for (int i = 0; i < cFacts.size(); i++){
			for(listOfItemsHeaders h : )
			Label label = new Label(0, i, listOfItemsHeaders.get(i));
		}

		
	}
}
