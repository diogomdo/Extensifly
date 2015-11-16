package com.codeglif.main;

public class EvalSpecs {
	
	public enum EvalFacts implements Evaluation{
		NEWOPERATION(1,1),
		OPERATIONALDIFF(2,3),
		NEWBLOCK(5,30),
		NEWCANVAS(4,20),
		NEWLOV(3,10),
		NEWITEM(4,15),
		PROPERDIFF(1,2);


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
	
	public void ExtensionDifficultyLevel(){
		int extTotal = 0;
		for (EvalFacts r : EvalFacts.values()){
			System.out.println(r);
		}
	}	

}
