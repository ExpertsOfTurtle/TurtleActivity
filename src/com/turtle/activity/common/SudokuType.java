package com.turtle.activity.common;

public enum SudokuType {
	
	BEGINNER("����"),JUNIOR("����"),SENIOR("�߼�"),MIDDLE("�м�"),MASTER("�ǻ�");
	
	private String val;
	
	public String toString() {
		return this.val;
	}
	private SudokuType(String val) {
		this.val = val;
	}
}
