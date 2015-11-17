package com.codeglif.main;

public class ChangeFacts {
	
	private String formName = "";
	private Integer totalNewOp = 0;
	private Integer totalOperationalDiff = 0;
	private Integer totalStructuralDiff = 0;
	private Integer totalNewBlock = 0;
	private Integer totalNewCanvas = 0;
	private Integer totalNewLov = 0;
	private Integer totalNewItems = 0;
	private Integer totalPropDiff = 0;
	
	public Integer getTotalPropDiff() {
		return totalPropDiff;
	}
	public void setTotalPropDiff(Integer totalPropDiff) {
		this.totalPropDiff = totalPropDiff;
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
	
	public void printValues() {
		System.out.println(this.getFormName());
		System.out.println("New Op "+this.getTotalNewOp());
		System.out.println("Op Diff "+this.getTotalOperationalDiff());
		System.out.println("Canvas "+this.getTotalNewCanvas());
		System.out.println("LOV "+this.getTotalNewLov());
		System.out.println("Block "+this.getTotalNewBlock());
		System.out.println("New Items "+this.getTotalNewItems());
		System.out.println("Diff Prop "+this.getTotalPropDiff()+"\n");
		
	}
}