package com.mvc.dto;

import java.sql.Date;

public class TestBookDTO {
	private int myidx; 
	private int contentid; 
	private String id; 
	private Date  reg_date;
	private int type; 
	private String deactivate;
	
	public int getMyidx() {
		return myidx;
	}
	public void setMyidx(int myidx) {
		this.myidx = myidx;
	}
	public int getContentid() {
		return contentid;
	}
	public void setContentid(int contentid) {
		this.contentid = contentid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDeactivate() {
		return deactivate;
	}
	public void setDeactivate(String deactivate) {
		this.deactivate = deactivate;
	} 
}
