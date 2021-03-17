package com.mvc.dto;

import java.util.Date;

public class QnaDTO {
	/*question*/
	private int qnaIdx;
	private String subject;
	private String content;
	private Date reg_date;
	private String id;
	private int rnum;
	
	/*answer*/
	private int ansIdx;
	private String subject_A;
	private String content_A;
	private Date reg_date_A;
	private String managerid;
	
	
	public int getAnsIdx() {
		return ansIdx;
	}
	public void setAnsIdx(int ansIdx) {
		this.ansIdx = ansIdx;
	}
	public String getSubject_A() {
		return subject_A;
	}
	public void setSubject_A(String subject_A) {
		this.subject_A = subject_A;
	}
	public String getContent_A() {
		return content_A;
	}
	public void setContent_A(String content_A) {
		this.content_A = content_A;
	}
	public Date getReg_date_A() {
		return reg_date_A;
	}
	public void setReg_date_A(Date reg_date_A) {
		this.reg_date_A = reg_date_A;
	}
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getQnaIdx() {
		return qnaIdx;
	}
	public void setQnaIdx(int qnaIdx) {
		this.qnaIdx = qnaIdx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
