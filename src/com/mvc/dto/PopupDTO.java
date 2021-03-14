package com.mvc.dto;

import java.sql.Date;

public class PopupDTO {
	private int infoidx;
	private String subject;
	private String managerid;
	private String content;
	private Date reg_date;
	private String popupalert;
	
	public int getInfoidx() {
		return infoidx;
	}
	public void setInfoidx(int infoidx) {
		this.infoidx = infoidx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getPopupalert() {
		return popupalert;
	}
	public void setPopupalert(String popupalert) {
		this.popupalert = popupalert;
	}
}
