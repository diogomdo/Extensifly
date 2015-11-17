package com.codeglif.main;

import java.util.ArrayList;

public class EvalSpecs {
	
	public enum EvalFacts implements Evaluation{
		NEWOPERATION(1,1),
		OPERATIONALDIFF(2,3),
		NEWBLOCK(5,30),
		NEWCANVAS(4,20),
		NEWLOV(3,10),
		NEWITEM(4,15),
		PROPDIFF(1,2);


		private int difficulteLevel;
		private int timeSpentValue;

		private EvalFacts(int diffValue, int timeValue) {
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

	}
	
	public EvalSpecs(ArrayList<ChangeFacts> changeFacts){
		extensionDifficultyLevel(changeFacts);
	}
	
	public EvalFacts find(String description){
		return null;
		
	}
	
	public void extensionDifficultyLevel(ArrayList<ChangeFacts> changeFacts){
		int extTotal = 0;
		
		for (ChangeFacts c : changeFacts){
			
		}
		for (EvalFacts r : EvalFacts.values()){
			System.out.println(r);
		}
	}	

}
