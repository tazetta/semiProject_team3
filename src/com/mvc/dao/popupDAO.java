package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.popupDTO;

public class popupDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public popupDAO() {
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
			if(rs != null) {	rs.close();}
			if(ps != null) {	ps.close();}
			if(conn != null) {	conn.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<popupDTO> popupList() {
		
		ArrayList<popupDTO> popupList = new ArrayList<popupDTO>();
		String sql = "SELECT infoidx, reg_date, managerid, subject, popupalert FROM popup"
				+" ORDER BY infoidx DESC";
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				popupDTO dto = new popupDTO();
				dto.setInfoidx(rs.getInt("infoidx"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setManagerid(rs.getString("managerid"));
				dto.setSubject(rs.getString("subject"));
				dto.setPopupalert(rs.getString("popupalert"));
				popupList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		return popupList;
	}
	
	
	public boolean popupWrite(popupDTO dto) {
		String sql = "INSERT INTO popup(infoidx,managerid,subject,content,popupalert)VALUES(popup_seq.NEXTVAL,?,?,?,?)";		
		boolean success = false;	
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getManagerid());
			ps.setString(2, dto.getSubject());
			ps.setString(3, dto.getContent());
			ps.setString(4, dto.getPopupalert());
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		System.out.println("팝업 등록 성공여부:"+success);
		return success;
	}

	public boolean popupDel(String infoidx) {
	
		String sql="DELETE FROM popup WHERE infoidx=?";
		boolean success = false;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, infoidx);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				resClose();
		}	
		System.out.println("삭제여부 :"+success);
		return success;
	}

	public popupDTO detail(String infoidx) {
		
		popupDTO dto = null;
		String sql="SELECT infoidx, managerid, subject, content, popupalert "+ 
				"FROM popup WHERE infoidx= ?";		
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, infoidx);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("rs : "+rs);
			if(rs.next()) {
				dto = new popupDTO();
				dto.setInfoidx(rs.getInt("infoidx"));
				dto.setManagerid(rs.getString("managerid"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setPopupalert(rs.getString("popupalert"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}			
		return dto;
	}

	
//	public int update(popupDTO dto) {
//		int success = -1;
//		String sql="UPDATE popup SET subject=?, content=?, popupalert=? WHERE infoidx=?";
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, dto.getSubject());
//			ps.setString(2, dto.getContent());
//			ps.setString(3, dto.getPopupalert());
//			ps.setInt(4, dto.getInfoidx());
//			success = ps.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			resClose();
//		}			
//		System.out.println("수정 여부 :"+success);
//		return success;
//	}


	public int update(String subject, String content, String popupalert, String infoidx) {
		int success = 0;
		String sql="UPDATE popup SET subject=?, content=?, popupalert=? WHERE infoidx=?";
		try {
		ps = conn.prepareStatement(sql);
		ps.setString(1, subject);
		ps.setString(2, content);
		ps.setString(3, popupalert);
		ps.setString(4, infoidx);
		success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}			
		return success;

	}
}