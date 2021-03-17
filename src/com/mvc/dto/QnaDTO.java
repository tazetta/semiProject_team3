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
	private String subjectA;
	private String contentA;
	private Date reg_dateA;
	private String managerid;
	
	
	public String getSubjectA() {
		return subjectA;
	}
	public void setSubjectA(String subjectA) {
		this.subjectA = subjectA;
	}
	public String getContentA() {
		return contentA;
	}
	public void setContentA(String contentA) {
		this.contentA = contentA;
	}
	public Date getReg_dateA() {
		return reg_dateA;
	}
	public void setReg_dateA(Date reg_dateA) {
		this.reg_dateA = reg_dateA;
	}
	public int getAnsIdx() {
		return ansIdx;
	}
	public void setAnsIdx(int ansIdx) {
		this.ansIdx = ansIdx;
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
