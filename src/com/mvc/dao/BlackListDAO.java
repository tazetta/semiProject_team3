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

import com.mvc.dto.BlackListDTO;
import com.mvc.dto.PopupDTO;

public class BlackListDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public BlackListDAO() {
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
	
	public HashMap<String, Object> memberBlackList(int page) {
		
		HashMap<String,Object> map= new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page*pagePerCnt;
		int start = end-(pagePerCnt-1);
		String sql = "SELECT blackidx, id, reason, reg_date, managerid FROM (" +
					 "SELECT ROW_NUMBER() OVER(ORDER BY blackidx DESC) " +
					 "AS rnum, blackidx, id, reason, reg_date, managerid "+
					 "FROM blacklist) WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<BlackListDTO> memberBlackList = new ArrayList<BlackListDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				BlackListDTO dto = new BlackListDTO();
				dto.setBlackidx(rs.getInt("blackidx"));
				dto.setId(rs.getString("id"));
				dto.setReason(rs.getString("reason"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setManagerid(rs.getString("managerid"));
				memberBlackList.add(dto);
			}
			int maxPage= getMaxPage(pagePerCnt);
			map.put("memberBlackList",memberBlackList);
			map.put("maxPage",maxPage);
			System.out.println("maxPage: "+ maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int getMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(blackidx) FROM blacklist";
		int max=0;
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

	public boolean memberBlackAdd(BlackListDTO dto) {
	
//		String sql = "UPDATE member SET blackstatus='TRUE' "+
//		" (INSERT INTO blacklist (blackidx,id,reason,managerid,blackstatus)"+
//		" VALUES (black_seq.NEXTVAL,?,?,?,?)) WHERE id=? ";
		
		String sql = "INSERT INTO blacklist (blackidx,id,reason,managerid) VALUES (black_seq.NEXTVAL,?,?,?)";
		boolean success = false;
		
		try {
			ps = conn.prepareStatement(sql);
			//ps.setInt(1, dto.getBlackidx());
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getReason());
			ps.setString(3, dto.getManagerid());
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				resClose();
		}	
		System.out.println("블랙리스트 추가 성공여부 :"+success);
		return success;
	}

	public BlackListDTO memberBlackDetail(String id) {
		
		BlackListDTO dto = null;

		String sql = "select b.reg_date, b.id, b.managerid, b.reason, b.blackstatus ,m.reportcnt, m.blackcnt, m.name, m.update_date"
					+ " from blacklist b, member m where b.id=m.id AND b.id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("rs : "+rs);
			if(rs.next()) {
				dto = new BlackListDTO();
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setManagerid(rs.getString("managerid"));
				dto.setReason(rs.getString("reason"));
				dto.setBlackstatus(rs.getString("blackstatus"));
				dto.setReportcnt(rs.getInt("reportcnt"));
				dto.setBlackcnt(rs.getInt("blackcnt"));
				dto.setName(rs.getString("name"));
				dto.setUpdate_date(rs.getDate("update_date"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}			
		return dto;
	}

	public BlackListDTO memberDetail(String id) {
		
		BlackListDTO dto = null;
		String sql="SELECT blackidx, id, reason, reg_date, managerid FROM blacklist WHERE id= ?";	
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("rs : "+rs);
			if(rs.next()) {
				dto = new BlackListDTO();
				dto.setBlackidx(rs.getInt("blackidx"));
				dto.setManagerid(rs.getString("id"));
				dto.setReason(rs.getString("reason"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setManagerid(rs.getString("managerid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}			
		return dto;
	}
	
	

}
