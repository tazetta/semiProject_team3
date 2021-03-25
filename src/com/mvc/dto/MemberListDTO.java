package com.mvc.dto;

import java.sql.Date;

public class MemberListDTO {

		private String id;
		private String pw;
		private String name;
		private String phone;
		private String email;
		private String withdraw;
		private int reportcnt;
		private Date reg_date;
		private Date update_date;
		private int blackcnt;
		
		private int blackidx;
		private String reason;
		private String managerid;
		private String blackstatus;
		
		private int staus_cnt;
		
		public int getStaus_cnt() {
			return staus_cnt;
		}
		public void setStaus_cnt(int staus_cnt) {
			this.staus_cnt = staus_cnt;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPw() {
			return pw;
		}
		public void setPw(String pw) {
			this.pw = pw;
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
		public String getWithdraw() {
			return withdraw;
		}
		public void setWithdraw(String withdraw) {
			this.withdraw = withdraw;
		}
		public int getReportcnt() {
			return reportcnt;
		}
		public void setReportcnt(int reportcnt) {
			this.reportcnt = reportcnt;
		}
		public Date getReg_date() {
			return reg_date;
		}
		public void setReg_date(Date reg_date) {
			this.reg_date = reg_date;
		}
		public Date getUpdate_date() {
			return update_date;
		}
		public void setUpdate_date(Date update_date) {
			this.update_date = update_date;
		}
		public int getBlackcnt() {
			return blackcnt;
		}
		public void setBlackcnt(int blackcnt) {
			this.blackcnt = blackcnt;
		}
		public int getBlackidx() {
			return blackidx;
		}
		public void setBlackidx(int blackidx) {
			this.blackidx = blackidx;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
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

		

}
