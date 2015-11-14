package com.codeglif.main;

import java.util.HashMap;

public class Evaluation {
	
	private HashMap formExtList;
	
	public enum RelativeDifficulty implements DifficultyLevel{
		NEWOPERATION(1),
		OPERATIONALDIFF(2),
		STRUCTURALDIFF(3),
		NEWBLOCK(5),
		NEWCANVAS(4),
		NEWLOV(3),
		NEWITEM(4),
		PROPERDIFF(1);
	
		private int difficulteLevel;
		private RelativeDifficulty(int diffValue) {
			difficulteLevel = diffValue;
		}
		
		@Override
		public int getTypeDifficultyLevel() {
			return difficulteLevel;
		}
	}
		
	public Evaluation (HashMap formExtList){
		this.formExtList = formExtList;
		
	}
	
	private Integer ExtensionDifficultyLevel(){
		int extTotal = 0;
		for (RelativeDifficulty r : RelativeDifficulty.values()){
			
		}
		return extTotal;
	}	

}
