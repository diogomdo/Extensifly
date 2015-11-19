package com.codeglif.main;

public class EvalSpecs {
	
	/*
	 * TODO - Implement
	 * In a near future it is good 
	 * practice implement builder pattern
	 */

	private double difficultyGrade;
	private double timeSpent;
	private double absDifficultyGrade;
	private String ExtensionName;
	
	private Utilities util;

	public enum EvalFacts implements Evaluation{
		NEWOPERATION(1,1),
		OPERATIONALDIFF(2,3),
		NEWBLOCK(5,30),
		NEWCANVAS(4,20),
		NEWLOV(3,10),
		NEWITEM(4,15),
		PROPDIFF(1,2);


		private int difficultyValue;
		private int timeSpentValue;

		private EvalFacts(int diffValue, int timeValue) {
			this.difficultyValue = diffValue;
			this.timeSpentValue = timeValue;
		}
		
		@Override
		public int getDifficultyLevel() {
			return difficultyValue;
		}

		@Override
		public int getTimeSpent() {
			return timeSpentValue;
		}

	}
	
	public EvalSpecs(ChangeFacts changeFacts){
			this.util = new Utilities();
			difficultyCalc(EvalFacts.values(), changeFacts);
			timeCalc(EvalFacts.values(), changeFacts);
	}
	
	public void difficultyCalc(EvalFacts[] evalFacts, ChangeFacts changeFacts){
		int changedItems = 0;
		int currItemDiffLvl = 0;
		int totalChangedItems = 0;
		int tempDifficultyGrade = 0;
		int absDiffu = 0;
		for (EvalFacts e : evalFacts){
			changedItems = util.getCorrespChangeValue(e, changeFacts);
			if (changedItems != 0){
				currItemDiffLvl = e.getDifficultyLevel();
				absDiffu =  absDiffu + currItemDiffLvl;
				totalChangedItems = changedItems + totalChangedItems;
				tempDifficultyGrade = changedItems*currItemDiffLvl;
				
				absDifficultyGrade = absDiffu;
				difficultyGrade = difficultyGrade + tempDifficultyGrade;
			}
		}
		difficultyGrade = difficultyGrade/totalChangedItems;
		
	}
	
	public void timeCalc(EvalFacts[] evalFacts, ChangeFacts changeFacts){
		int changeVal = 0;
		int currItemTimeVal = 0;
		int tempTimeSpent = 0;
		for (EvalFacts e : evalFacts){
			changeVal = util.getCorrespChangeValue(e, changeFacts);
			currItemTimeVal = e.getTimeSpent();
			tempTimeSpent = changeVal*currItemTimeVal;
			timeSpent = timeSpent + tempTimeSpent;
		}
	}
	
//	public Integer getCorrespChangeValue(EvalFacts evalFacts, ChangeFacts changeFacts){
//		switch (evalFacts){
//		
//			case NEWOPERATION:
//				return changeFacts.getTotalNewOp();
//			case OPERATIONALDIFF:
//				return changeFacts.getTotalOperationalDiff();
//			case NEWBLOCK:
//				return changeFacts.getTotalNewBlock();
//			case NEWCANVAS:
//				return changeFacts.getTotalNewCanvas();
//			case NEWLOV:
//				return changeFacts.getTotalNewLov();
//			case NEWITEM:
//				return changeFacts.getTotalNewItems();
//			case PROPDIFF:
//				return changeFacts.getTotalPropDiff();
//			default: 
//				System.out.println("Property not valid, "+ evalFacts);
//				return null;
//		}
//	}
	
	public String getExtensionName() {
		return ExtensionName;
	}

	public void setExtensionName(String extensionName) {
		ExtensionName = extensionName;
	}
	
	public void print(){
		
		System.out.println(getExtensionName());
		System.out.println("Diff rel index: "+this.difficultyGrade);
		System.out.println("Diff abs index: "+this.absDifficultyGrade);
		System.out.println("Time index: "+this.timeSpent+"\n");
	}

}
