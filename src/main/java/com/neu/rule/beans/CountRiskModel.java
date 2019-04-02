package com.neu.rule.beans;

public class CountRiskModel {
	private String name;
	private long value;
	
	public CountRiskModel(){}

	public CountRiskModel(String name, long value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CountRiskModel [name=" + name + ", value=" + value + "]";
	};
	
}
