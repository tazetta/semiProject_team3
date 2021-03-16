package com.mvc.dto;

import java.util.Date;

public class BlackListDTO {

	private int blackidx;
	private String id;
	private String reason;
	private Date reg_date;
	private String managerid;
	private String blackstatus;
	
	private String name;
	private String phone;
	private String email;
	private int reportcnt;
	private int blackcnt;
	
	public int getBlackidx() {
		return blackidx;
	}
	public void setBlackidx(int blackidx) {
		this.blackidx = blackidx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public String getBlackstatus() {
		return blackstatus;
	}
	public void setBlackstatus(String blackstatus) {
		this.blackstatus = blackstatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getReportcnt() {
		return reportcnt;
	}
	public void setReportcnt(int reportcnt) {
		this.reportcnt = reportcnt;
	}
	public int getBlackcnt() {
		return blackcnt;
	}
	public void setBlackcnt(int blackcnt) {
		this.blackcnt = blackcnt;
	}
	
	
	
}
