package com.turtle.activity.beans;

import com.frc.appleframework.beans.AppleRequest;

public class SudokuActivityRequest extends AppleRequest {
	protected String type;
	protected int second;
	protected String username;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
