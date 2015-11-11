package com.codeglif.main;

import java.util.HashMap;

public class FormChangesFacts{
	
	private String formName = "";
	private Integer totalNewOp = 0;
	private Integer totalOperationalDiff = 0;
	private Integer totalStructuralDiff = 0;
	private Integer totalNewBlock = 0;
	private Integer totalNewCanvas = 0;
	private Integer totalNewLov = 0;
	private Integer totalNewItems = 0;
	private HashMap<String, Integer> structDiff;
	
	public FormChangesFacts(){
	}
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public Integer getTotalNewOp() {
		return totalNewOp;
	}
	public void setTotalNewOp(Integer totalNewOp) {
		this.totalNewOp = totalNewOp;
	}
	public Integer getTotalOperationalDiff() {
		return totalOperationalDiff;
	}
	public void setTotalOperationalDiff(Integer totalOperationalDiff) {
		this.totalOperationalDiff = totalOperationalDiff;
	}
	public Integer getTotalStructuralDiff() {
		return totalStructuralDiff;
	}
	public void setTotalStructuralDiff(Integer totalStructuralDiff) {
		this.totalStructuralDiff = totalStructuralDiff;
	}
	public HashMap<String, Integer> getStructDiff() {
		return structDiff;
	}
	public void setStructDiff(HashMap<String, Integer> structDiff) {
		this.structDiff = structDiff;
	}
	public Integer getTotalNewBlock() {
		return totalNewBlock;
	}

	public void setTotalNewBlock(Integer totalNewBlock) {
		this.totalNewBlock = totalNewBlock;
	}

	public Integer getTotalNewCanvas() {
		return totalNewCanvas;
	}

	public void setTotalNewCanvas(Integer totalNewCanvas) {
		this.totalNewCanvas = totalNewCanvas;
	}

	public Integer getTotalNewLov() {
		return totalNewLov;
	}

	public void setTotalNewLov(Integer totalNewLov) {
		this.totalNewLov = totalNewLov;
	}

	public Integer getTotalNewItems() {
		return totalNewItems;
	}

	public void setTotalNewItems(Integer totalNewItems) {
		this.totalNewItems = totalNewItems;
	}

	public void getAllFormFacts(){
		
		System.out.println("FORM: "+ this.formName);
		System.out.println("New Operations: "+this.getTotalNewOp());
		System.out.println("Opperation Diffs: "+this.getTotalOperationalDiff());
		System.out.println("Structural Diffs: "+this.getTotalStructuralDiff()+"\n");
		
	}
	
	

}