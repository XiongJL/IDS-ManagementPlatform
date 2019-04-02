package com.neu.rule.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ids_rule")
public class IdsRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int nvid;

	private String rule1;
	private String rule2;
	private String rule3;
	private String rule4;
	private String rule5;
	private String rule6;
	private String rule7;
	private String rule8;
	private String rule9;
	
	//流行度字段
	//流行(0),不流行(1),日本流行(2),欧美流行(3),国际流行(4)
	//默认是  0 
	private int popular;
	
	public int getPopular() {
		return popular;
	}

	public void setPopular(int popular) {
		this.popular = popular;
	}

	private int ids_flag;

	private int ids_origin;

	private int default_enable;

	private String cve_index;

	private String bugtraq_index;

	private String cnvd_index;

	private String another_index;

	private String cn_name;

	private String english_name;
	/*date类型进行注解序列化,让前端得到正确的时间 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08")
	@Column(name = "vul_date")
	private Date vul_date;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08")
	/*由于SORT排序时,对 下划线会忽略,所以改为没有下划线*/
	@Column(name = "publish_date")
	private Date publishdate;

	private String location;

	private String attack_target;

	private String category;

	private String risk;

	private String vul_status;

	private String current;
	/* 限制在255字节 */
	private String pcap_path1;
	private String pcap_path2;
	private String pcap_path3;
	private String pcap_path4;
	private String pcap_path5;
	private String other_path;

	private String en_des;
	private String cn_des;

	private String ni_test;

	private String patch;
	private String patchen;

	private String mome;

	public Date getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}

	public String getPatchen() {
		return patchen;
	}

	public void setPatchen(String patchen) {
		this.patchen = patchen;
	}

	private String protocol;

	private String target_system;

	public int getNvid() {
		return nvid;
	}

	public int getIds_flag() {
		return ids_flag;
	}

	public void setIds_flag(int ids_flag) {
		this.ids_flag = ids_flag;
	}

	public int getIds_origin() {
		return ids_origin;
	}

	public void setIds_origin(int ids_origin) {
		this.ids_origin = ids_origin;
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

	public int getDefault_enable() {
		return default_enable;
	}

	public void setDefault_enable(int default_enable) {
		this.default_enable = default_enable;
	}

	public String getCve_index() {
		return cve_index;
	}

	public void setCve_index(String cve_index) {
		this.cve_index = cve_index;
	}

	public String getBugtraq_index() {
		return bugtraq_index;
	}

	public void setBugtraq_index(String bugtraq_index) {
		this.bugtraq_index = bugtraq_index;
	}

	public String getCnvd_index() {
		return cnvd_index;
	}

	public void setCnvd_index(String cnvd_index) {
		this.cnvd_index = cnvd_index;
	}

	public String getAnother_index() {
		return another_index;
	}

	public void setAnother_index(String another_index) {
		this.another_index = another_index;
	}

	public String getCn_name() {
		return cn_name;
	}

	public void setCn_name(String cn_name) {
		this.cn_name = cn_name;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public Date getVul_date() {
		return vul_date;
	}

	public void setVul_date(Date vul_date) {
		this.vul_date = vul_date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAttack_target() {
		return attack_target;
	}

	public void setAttack_target(String attack_target) {
		this.attack_target = attack_target;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getVul_status() {
		return vul_status;
	}

	public void setVul_status(String vul_status) {
		this.vul_status = vul_status;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getPcap_path1() {
		return pcap_path1;
	}

	public void setPcap_path1(String pcap_path1) {
		this.pcap_path1 = pcap_path1;
	}

	public String getPcap_path2() {
		return pcap_path2;
	}

	public void setPcap_path2(String pcap_path2) {
		this.pcap_path2 = pcap_path2;
	}

	public String getPcap_path3() {
		return pcap_path3;
	}

	public void setPcap_path3(String pcap_path3) {
		this.pcap_path3 = pcap_path3;
	}

	public String getPcap_path4() {
		return pcap_path4;
	}

	public void setPcap_path4(String pcap_path4) {
		this.pcap_path4 = pcap_path4;
	}

	public String getPcap_path5() {
		return pcap_path5;
	}

	public void setPcap_path5(String pcap_path5) {
		this.pcap_path5 = pcap_path5;
	}

	public String getOther_path() {
		return other_path;
	}

	public void setOther_path(String other_path) {
		this.other_path = other_path;
	}

	public String getEn_des() {
		return en_des;
	}

	public void setEn_des(String en_des) {
		this.en_des = en_des;
	}

	public String getCn_des() {
		return cn_des;
	}

	public void setCn_des(String cn_des) {
		this.cn_des = cn_des;
	}

	public String getNi_test() {
		return ni_test;
	}

	public void setNi_test(String ni_test) {
		this.ni_test = ni_test;
	}

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}

	public String getMome() {
		return mome;
	}

	public void setMome(String mome) {
		this.mome = mome;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getTarget_system() {
		return target_system;
	}

	public void setTarget_system(String target_system) {
		this.target_system = target_system;
	}

	@Override
	public String toString() {
		return "IdsRule [nvid=" + nvid + ", rule1=" + rule1 + ", rule2=" + rule2 + ", rule3=" + rule3 + ", rule4="
				+ rule4 + ", rule5=" + rule5 + ", rule6=" + rule6 + ", rule7=" + rule7 + ", rule8=" + rule8 + ", rule9="
				+ rule9 + ", popular=" + popular + ", ids_flag=" + ids_flag + ", ids_origin=" + ids_origin
				+ ", default_enable=" + default_enable + ", cve_index=" + cve_index + ", bugtraq_index=" + bugtraq_index
				+ ", cnvd_index=" + cnvd_index + ", another_index=" + another_index + ", cn_name=" + cn_name
				+ ", english_name=" + english_name + ", vul_date=" + vul_date + ", publish_date=" + publishdate
				+ ", location=" + location + ", attack_target=" + attack_target + ", category=" + category + ", risk="
				+ risk + ", vul_status=" + vul_status + ", current=" + current + ", pcap_path1=" + pcap_path1
				+ ", pcap_path2=" + pcap_path2 + ", pcap_path3=" + pcap_path3 + ", pcap_path4=" + pcap_path4
				+ ", pcap_path5=" + pcap_path5 + ", other_path=" + other_path + ", en_des=" + en_des + ", cn_des="
				+ cn_des + ", ni_test=" + ni_test + ", patch=" + patch + ", mome=" + mome + ", protocol=" + protocol
				+ ", target_system=" + target_system + "]";
	}

}
