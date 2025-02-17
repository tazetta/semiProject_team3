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

import com.mvc.dto.PopupDTO;

public class PopupDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public PopupDAO() {
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
	
	public HashMap<String, Object> popupList(int page) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		String sql = "SELECT infoidx, reg_date, managerid, subject, popupalert FROM ("
				+"SELECT ROW_NUMBER() OVER(ORDER BY popupalert DESC, infoidx DESC)" 
				+ "AS rnum, infoidx, reg_date, managerid, subject, popupalert "
				+"FROM popup) WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<PopupDTO> popupList = new ArrayList<PopupDTO>();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				PopupDTO dto = new PopupDTO();
				dto.setInfoidx(rs.getInt("infoidx"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setManagerid(rs.getString("managerid"));
				dto.setSubject(rs.getString("subject"));
				dto.setPopupalert(rs.getString("popupalert"));
				popupList.add(dto);
			}
			int maxPage = getMaxPage(pagePerCnt);
			map.put("popupList", popupList);
			map.put("maxPage", maxPage);
			System.out.println("maxPage: " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		return map;
	}
	
	private int getMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(infoidx) FROM popup";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return max;
	}
	
	public boolean popupWrite(PopupDTO dto) {
		String sql = "INSERT INTO popup(infoidx,managerid,subject,content)VALUES(popup_seq.NEXTVAL,?,?,?)";		
		boolean success = false;	
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getManagerid());
			ps.setString(2, dto.getSubject());
			ps.setString(3, dto.getContent());
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

	public PopupDTO detail(String infoidx) {
		
		PopupDTO dto = null;
		String sql="SELECT infoidx, managerid, subject, content, popupalert "+ 
				"FROM popup WHERE infoidx= ?";		
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, infoidx);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("rs : "+rs);
			if(rs.next()) {
				dto = new PopupDTO();
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

	
	public boolean update(PopupDTO dto) {
		boolean success = false;
		String sql="UPDATE popup SET popupalert='NO'";
		try {
			ps = conn.prepareStatement(sql);
			int a = ps.executeUpdate();
			System.out.println(a);
			
			sql="UPDATE popup SET managerid=?, subject=?, content=?, popupalert=? WHERE infoidx=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getManagerid());
			ps.setString(2, dto.getSubject());
			ps.setString(3, dto.getContent());
			ps.setString(4, dto.getPopupalert());
			ps.setInt(5, dto.getInfoidx());
			if(ps.executeUpdate()>0) {
				success = true;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}			
		System.out.println("수정 여부 :"+success);
		return success;
	}

	public PopupDTO popupMain() {
		boolean success = false;
		PopupDTO dto = null;
		String sql = "SELECT subject,content FROM popup WHERE popupalert='YES'";
		try {
			ps  = conn.prepareStatement(sql);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new PopupDTO();
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		return dto;
	}
}