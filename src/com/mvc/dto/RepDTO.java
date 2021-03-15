package com.mvc.dto;

import java.sql.Date;

public class RepDTO {
	/// 보드용
	private int bbsRepIdx;
	private int boardIdx;
	/// 댓글용
	private int commentRepIdx;
	private int reIdx;
	
	private String reason;
	private String id;
	private Date reg_date;
	private String deactivate;
	private String managerId;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getBbsRepIdx() {
		return bbsRepIdx;
	}
	public void setBbsRepIdx(int bbsRepIdx) {
		this.bbsRepIdx = bbsRepIdx;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public int getCommentRepIdx() {
		return commentRepIdx;
	}
	public void setCommentRepIdx(int commentRepIdx) {
		this.commentRepIdx = commentRepIdx;
	}
	public int getReIdx() {
		return reIdx;
	}
	public void setReIdx(int reIdx) {
		this.reIdx = reIdx;
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
	public String getDeactivate() {
		return deactivate;
	}
	public void setDeactivate(String deactivate) {
		this.deactivate = deactivate;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	
}
