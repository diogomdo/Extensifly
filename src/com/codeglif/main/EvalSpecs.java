package com.codeglif.main;

import java.util.ArrayList;

public class EvalSpecs {

	private double difficultyGrade;
	private double timeSpent;
	private String formName;
	private ArrayList<EvalSpecs> evalSpecsList;
	
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
	
	public EvalSpecs(ArrayList<ChangeFacts> changeFacts){
		for (ChangeFacts currentExtension : changeFacts){
			EvalSpecs currentEval;
			formName = currentExtension.getFormName();
			difficultyCalc(EvalFacts.values(), currentExtension);
			timeCalc(EvalFacts.values(), currentExtension);
			evalSpecsList.add(this);
		}
		
	}
	
	public void difficultyCalc(EvalFacts[] evalFacts, ChangeFacts changeFacts){
		int changeVal = 0;
		int currItemDiffVal = 0;
		int sumDiffVal = 0;
		for (EvalFacts e : evalFacts){
			changeVal = getCorrespChangeValue(e, changeFacts);
			currItemDiffVal = e.getDifficultyLevel();
			sumDiffVal =+ e.getDifficultyLevel();
			difficultyGrade =+ changeVal*currItemDiffVal;
		}
		difficultyGrade = difficultyGrade/sumDiffVal;
		
	}
	
	public void timeCalc(EvalFacts[] evalFacts, ChangeFacts changeFacts){
		int changeVal = 0;
		int currItemTimeVal = 0;
		int sumTimeVal = 0;
		for (EvalFacts e : evalFacts){
			changeVal = getCorrespChangeValue(e, changeFacts);
			currItemTimeVal = e.getTimeSpent();
			timeSpent =+ changeVal*currItemTimeVal;
		}
		timeSpent = timeSpent/sumTimeVal;
		
	}
	
	public Integer getCorrespChangeValue(EvalFacts evalFacts, ChangeFacts changeFacts){
		switch (evalFacts){
		
			case NEWOPERATION:
				return changeFacts.getTotalOperationalDiff();
			case OPERATIONALDIFF:
				return changeFacts.getTotalOperationalDiff();
			case NEWBLOCK:
				return changeFacts.getTotalOperationalDiff();
			case NEWCANVAS:
				return changeFacts.getTotalOperationalDiff();
			case NEWLOV:
				return changeFacts.getTotalOperationalDiff();
			case NEWITEM:
				return changeFacts.getTotalOperationalDiff();
			case PROPDIFF:
				return changeFacts.getTotalOperationalDiff();
			default: 
				System.out.println("Property not valid, "+ evalFacts);
				return null;
		}
	}	

}
