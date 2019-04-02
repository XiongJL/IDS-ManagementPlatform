package com.neu.rule.beans;
/**
 * 只查询某几个字段的model载体
 * @author Administrator
 *
 */
public class IdsFindModel {
	private int nvid;
	
	private int ids_flag;
	
	public int getIds_flag() {
		return ids_flag;
	}
	public void setIds_flag(int ids_flag) {
		this.ids_flag = ids_flag;
	}
	private String rule1;
	private String rule2;
	private String rule3;
	private String rule4;
	private String rule5;
	private String rule6;
	private String rule7;
	private String rule8;
	private String rule9;
	
	public IdsFindModel(){};
	public IdsFindModel(int nvid,int ids_flag,String rule1, String rule2, String rule3, String rule4, String rule5, String rule6,
			String rule7, String rule8, String rule9) {
		super();
		this.nvid = nvid;
		this.ids_flag = ids_flag;
		this.rule1 = rule1;
		this.rule2 = rule2;
		this.rule3 = rule3;
		this.rule4 = rule4;
		this.rule5 = rule5;
		this.rule6 = rule6;
		this.rule7 = rule7;
		this.rule8 = rule8;
		this.rule9 = rule9;
	}
	public int getNvid() {
		return nvid;
	}
	public void setNvid(int nvid) {
		this.nvid = nvid;
	}
	public String getRule1() {
		return rule1;
	}
	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}
	public String getRule2() {
		return rule2;
	}
	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}
	public String getRule3() {
		return rule3;
	}
	public void setRule3(String rule3) {
		this.rule3 = rule3;
	}
	public String getRule4() {
		return rule4;
	}
	public void setRule4(String rule4) {
		this.rule4 = rule4;
	}
	public String getRule5() {
		return rule5;
	}
	public void setRule5(String rule5) {
		this.rule5 = rule5;
	}
	public String getRule6() {
		return rule6;
	}
	public void setRule6(String rule6) {
		this.rule6 = rule6;
	}
	public String getRule7() {
		return rule7;
	}
	public void setRule7(String rule7) {
		this.rule7 = rule7;
	}
	public String getRule8() {
		return rule8;
	}
	public void setRule8(String rule8) {
		this.rule8 = rule8;
	}
	public String getRule9() {
		return rule9;
	}
	public void setRule9(String rule9) {
		this.rule9 = rule9;
	}
	@Override
	public String toString() {
		return "IdsFindModel [nvid=" + nvid + ", ids_flag=" + ids_flag + ", rule1=" + rule1 + ", rule2=" + rule2
				+ ", rule3=" + rule3 + ", rule4=" + rule4 + ", rule5=" + rule5 + ", rule6=" + rule6 + ", rule7=" + rule7
				+ ", rule8=" + rule8 + ", rule9=" + rule9 + "]";
	}
	
	
}
