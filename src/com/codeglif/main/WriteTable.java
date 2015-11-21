package com.codeglif.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	private String filePath = "./Export/";
	private ArrayList<ChangeFacts> cFacts;
	private ArrayList<EvalSpecs> eFacts;
	private ArrayList<String> listOfItemsHeaders = new ArrayList<>();
	private Utilities util;
	WritableSheet extensionReportSheet = null;
	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	
	public WriteTable(ArrayList<ChangeFacts> changeFacts, ArrayList<EvalSpecs> listOfEvalSpecs){
		this.util = new Utilities();
		this.eFacts = listOfEvalSpecs;
		this.cFacts = changeFacts;
	}
	
	public void write() throws IOException, WriteException {
	    File file = new File(filePath+"output"+timeStamp+".xls");
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    WritableSheet extensionReportSheet = workbook.createSheet("Extension Report", 0);
	    setTableHeaders(extensionReportSheet);
	    contentFiller(cFacts, extensionReportSheet);
	    workbook.write();
	    workbook.close();
	  }
	
	public void setTableHeaders(WritableSheet reportSheet) throws WriteException, WriteException{
		EvalFacts[] ExtFactsNames = EvalSpecs.EvalFacts.values();
		
		 for (int i = 0; i < ExtFactsNames.length; i++){
			 this.listOfItemsHeaders.add(ExtFactsNames[i].name());
		 }
		 this.listOfItemsHeaders.add(0, "NAME");
		 this.listOfItemsHeaders.add(listOfItemsHeaders.size(), "DIFF REL");
		 this.listOfItemsHeaders.add(listOfItemsHeaders.size(), "DIFF ABS");
		 this.listOfItemsHeaders.add(listOfItemsHeaders.size(), "TIME");
		 
		 for (int i = 0; i < listOfItemsHeaders.size(); i++) {
             Label label = new Label(i, 0, listOfItemsHeaders.get(i));
             reportSheet.addCell(label);
             reportSheet.getWritableCell(i, 0);
         }
	}
	
	public void contentFiller(ArrayList<ChangeFacts> cFacts, WritableSheet extensionReportSheet) throws RowsExceededException, WriteException{
		
		/*loop for the extensions - lines*/
		for (int line = 0; line < cFacts.size(); line++){
			/*add name*/
			Label extName = new Label(0, line+1, cFacts.get(line).getFormName());
			extensionReportSheet.addCell(extName);
			for (int j = 0; j < this.listOfItemsHeaders.size(); j++){
				
				if (!listOfItemsHeaders.get(j).equals("NAME")){
					
					if (listOfItemsHeaders.get(j).equals("DIFF REL")){
						Label diffRel = new Label(j, line+1, String.valueOf(eFacts.get(line).difficultyGrade));
						extensionReportSheet.addCell(diffRel);
					}else if (listOfItemsHeaders.get(j).equals("DIFF ABS")){
						Label diffAbs = new Label(j, line+1, String.valueOf(eFacts.get(line).absDifficultyGrade));
						extensionReportSheet.addCell(diffAbs);
					}else if (listOfItemsHeaders.get(j).equals("TIME")){
						Label time = new Label(j, line+1, String.valueOf(eFacts.get(line).timeSpent));
						extensionReportSheet.addCell(time);
					}else {
						Label content = new Label(j, line+1, util.getCorrespChangeValue(listOfItemsHeaders.get(j) ,cFacts.get(line)).toString());
						extensionReportSheet.addCell(content);
					}

				}
			}
		}
	}
}
