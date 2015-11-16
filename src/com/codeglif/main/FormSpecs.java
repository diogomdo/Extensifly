package com.codeglif.main;

public class FormSpecs {
	
	private String formName = "";
	CHANGEFACTS changeFacts;
	
	public enum CHANGEFACTS implements Evaluation{
		NEWOPERATION(1,1),
		OPERATIONALDIFF(2,3),
		STRUCTURALDIFF(3,0),
		NEWBLOCK(5,30),
		NEWCANVAS(4,20),
		NEWLOV(3,10),
		NEWITEM(4,15),
		PROPERDIFF(1,2);


		private int difficulteLevel;
		private int timeSpentValue;
		private int occurValue;

		private CHANGEFACTS(int diffValue, int timeValue) {
			this.difficulteLevel = diffValue;
			this.timeSpentValue = timeValue;
		}
		
		@Override
		public int getDifficultyLevel() {
			return difficulteLevel;
		}

		@Override
		public int getTimeSpent() {
			return timeSpentValue;
		}

		@Override
		public void setTotalOcur(int value) {
			this.occurValue = value;
			
		}

		@Override
		public int getTotalOcur() {
			return occurValue;
		}
	}
		
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public void ExtensionDifficultyLevel(){
		int extTotal = 0;
		for (CHANGEFACTS r : CHANGEFACTS.values()){
			System.out.println(r+" : "+r.getTotalOcur());
		}
		System.out.println(formName+"\n");
	}	

}
