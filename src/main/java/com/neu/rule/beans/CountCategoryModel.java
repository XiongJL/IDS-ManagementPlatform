package com.neu.rule.beans;
/**
 * 接收Category计数
 * @author XiongJL
 *
 */
public class CountCategoryModel {
	private String name;
	private long value;
	private boolean Top = false;
	
	public CountCategoryModel(){};
	public CountCategoryModel(String name, long value) {
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
	public boolean isTop() {
		return Top;
	}
	public void setTop(boolean top) {
		Top = top;
	}

	
	
}
