package com.turtle.activity.common;

public enum SudokuType {
	
	BEGINNER("入门"),JUNIOR("初级"),SENIOR("高级"),MIDDLE("中级"),MASTER("骨灰");
	
	private String val;
	
	public String toString() {
		return this.val;
	}
	private SudokuType(String val) {
		this.val = val;
	}
}
