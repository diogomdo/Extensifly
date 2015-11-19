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
	
	public WriteTable(ArrayList<ChangeFacts> changeFacts){
		this.cFacts = changeFacts;
	}
	
	public void write() throws IOException, WriteException {
	    File file = new File(fileName);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    WritableSheet extensionReportSheet = workbook.createSheet("Extension Report", 0);
	    setTableHeaders();
	    workbook.write();
	    workbook.close();
	  }
	
	public void setTableHeaders() throws WriteException, WriteException{
		EvalFacts[] ExtFactsNames = EvalSpecs.EvalFacts.values();
		String names[] = new String[ExtFactsNames.length];
		 for (int i = 0; i < ExtFactsNames.length; i++){
			 names[i] = ExtFactsNames[i].name();
		 }
		 
		 for (int i = 0; i < names.length; i++) {
             Label label = new Label(i, 0, names[i]);
             extensionReportSheet.addCell(label);
             WritableCell cell = extensionReportSheet.getWritableCell(i, 0);
         }
	}

	
}
