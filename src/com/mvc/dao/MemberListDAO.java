package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.MemberListDTO;
import com.mvc.dto.PopupDTO;

public class MemberListDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public MemberListDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<MemberListDTO> memberList() {
		
		ArrayList<MemberListDTO> memberList = new ArrayList<MemberListDTO>();
		String sql = "SELECT reg_date, id, name, phone, email FROM member WHERE id NOT IN ('admin') ORDER BY reg_date DESC";
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberListDTO dto = new MemberListDTO();
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				memberList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return memberList;
	}

	public MemberListDTO memberDetail(String id) {
		
		MemberListDTO dto = null;
		String sql = "SELECT reg_date, id, name, phone, email, withdraw, reportcnt, update_date, blackcnt FROM member WHERE id=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("rs : "+rs);
			if(rs.next()) {
				dto = new MemberListDTO();
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				dto.setWithdraw(rs.getString("withdraw"));
				dto.setReportcnt(rs.getInt("reportcnt"));
				dto.setUpdate_date(rs.getDate("update_date"));
				dto.setBlackcnt(rs.getInt("blackcnt"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}			
		return dto;
	}
}
