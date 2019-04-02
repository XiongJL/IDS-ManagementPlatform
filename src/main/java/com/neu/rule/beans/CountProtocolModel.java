package com.neu.rule.beans;
/**
 * 用来接收协议类型的计数
 * @author Administrator
 *
 */
public class CountProtocolModel {
//	private String protocol;
//	private long count;
//根据前端的需求,更改命名为以下
	private String name;
	private long value;
	public CountProtocolModel() {}
	public CountProtocolModel(String name, long value) {
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
		return "CountProtocol [name=" + name + ", value=" + value + "]";
	};
	
}
