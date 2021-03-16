package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.MemberListDTO;

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

	public HashMap<String, Object> memberList(int page) {
		
		HashMap<String,Object> map= new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page*pagePerCnt;
		int start = end-(pagePerCnt-1);
		String sql = "SELECT reg_date, id, name, phone, email FROM (" +
					 "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) " +
					 "AS rnum, reg_date, id, name, phone, email "+
					 "FROM member WHERE id NOT IN ('admin')) WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<MemberListDTO> memberList = new ArrayList<MemberListDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
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
			int type=1;
			int maxPage= getMaxPage(pagePerCnt,type);
			map.put("memberList",memberList);
			map.put("maxPage",maxPage);
			System.out.println("maxPage: "+ maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int getMaxPage(int pagePerCnt,int type) {
		String sql = "SELECT COUNT(id) FROM member WHERE id NOT IN ('admin')";
		int max=0;
		if(type==2) {
			sql = "SELECT COUNT(id) FROM member WHERE withdraw='TRUE'";
		}
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
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

	public HashMap<String, Object> memberDelList(int page) {
		
		HashMap<String,Object> map= new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page*pagePerCnt;
		int start = end-(pagePerCnt-1);
		
		String sql = "SELECT reg_date, withdraw, id, name, phone, email FROM (" +
				 "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) " +
				 "AS rnum, reg_date, withdraw,  id, name, phone, email "+
				 "FROM member WHERE withdraw='TRUE'"
				 + ") WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<MemberListDTO> memberDelList = new ArrayList<MemberListDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberListDTO dto = new MemberListDTO();
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setWithdraw(rs.getString("withdraw"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				memberDelList.add(dto);
			}
			int type=2;
			int maxPage= getMaxPage(pagePerCnt,type);
			map.put("memberDelList",memberDelList);
			map.put("maxPage",maxPage);
			System.out.println("maxPage: "+ maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	public boolean memberDraw(String id) {
		
		String sql = "DELETE FROM member WHERE id=?";
		boolean success = false;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				resClose();
		}	
		System.out.println("회원 삭제여부 :"+success);
		return success;
	}

	public boolean memberRestore(String id) {
		
		String sql = "UPDATE member SET withdraw='FALSE' WHERE id=?";
		boolean success = false;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				resClose();
		}	
		System.out.println("회원 복구 성공여부 :"+success);
		return success;
	}

	public ArrayList<MemberListDTO> memberSearch() {
		
		return null;
	}

	

}
