package com.example.user.glujam;

public class DataDto {
	private int type;
	private int value;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "GapTime1 [type=" + type + ", value=" + value + "]";
	}
	
	
}
