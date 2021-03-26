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

import com.mvc.dto.RepDTO;
import com.mvc.dto.BookmarkDTO;
import com.mvc.dto.TripDTO;

public class TripDetailDAO {

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;



public TripDetailDAO() {		
		

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
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public TripDTO tripDetail(String conIdx) {
		TripDTO dto = null;
		// 지역코드 가져와야함.

		try {

			String sql = "SELECT title,reg_date,firstimage,deactivate,overview,LATITUDE,LONGITUDE"
					+ "    ,(SELECT name FROM area WHERE areacode=(SELECT areacode FROM trip WHERE contentid=?)) AS area"
					+ "    ,bookmarkCnt"
					+ "    FROM trip WHERE contentid=? AND deactivate='FALSE'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, conIdx);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("firstimage"));
				dto = new TripDTO();
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setFirstImage(rs.getString("firstimage"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setArea(rs.getString("area"));
				dto.setBookmark(rs.getInt("bookmarkCnt"));
				dto.setOverview(rs.getString("overview"));
				dto.setLatitude(rs.getString("LATITUDE"));
				dto.setLongitude(rs.getString("LONGITUDE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public BookmarkDTO bookmark(String conIdx, String id) {
		// id 받아와야함.
		System.out.println(conIdx);
		String sql = "SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=1";
		BookmarkDTO dto = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new BookmarkDTO();
				dto.setMyidx(rs.getInt("myidx"));
				dto.setContentid(rs.getInt("contentid"));
				dto.setId(rs.getString("id"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setType(rs.getInt("type"));
				dto.setDeactivate(rs.getString("deactivate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto);
		return dto;
	}

	public BookmarkDTO visit(String conIdx, String id) {
		// id 받아와야함.
		String sql = "SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=2";
		BookmarkDTO dto = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new BookmarkDTO();
				dto.setMyidx(rs.getInt("myidx"));
				dto.setContentid(rs.getInt("contentid"));
				dto.setId(rs.getString("id"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setType(rs.getInt("type"));
				dto.setDeactivate(rs.getString("deactivate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto);
		return dto;
	}

	public int addDel(BookmarkDTO bdto) {
		int suc = 0;
		String sql = "INSERT INTO bookmark (myIdx, contentId, id, type) VALUES (bookmark_seq.NEXTVAL,?,?,?)";
		try {
			
			if (bdto.getMyidx() > 0) {
				System.out.println("업데이트");
				sql = "SELECT deactivate FROM bookmark WHERE myidx=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, bdto.getMyidx());
				rs= ps.executeQuery();
				String deact="";
				if(rs.next()) {
					deact =rs.getString(1);
					System.out.println("디 엑티비티 : "+deact+bdto.getDeactivate());
				}
				if(deact.equals(bdto.getDeactivate())) {
					suc=0;
				}else {
					sql = "UPDATE bookmark SET deactivate='TRUE' WHERE myidx=? AND id=? AND contentId=?";
					if (bdto.getDeactivate().equals("FALSE")) {
						sql = "UPDATE bookmark SET deactivate='FALSE' WHERE myidx=? AND id=? AND contentId=?";
					}
					ps = conn.prepareStatement(sql);
					ps.setInt(1, bdto.getMyidx());
					ps.setString(2, bdto.getId());
					ps.setInt(3, bdto.getContentid());		
					suc = ps.executeUpdate();
				}
			} else {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, bdto.getContentid());
				ps.setString(2, bdto.getId());
				ps.setInt(3, bdto.getType());
				suc = ps.executeUpdate();
				
			}
			System.out.println("추가,수정 : " + suc);
			if(suc>0) {
				if(bdto.getType()==1) {
					sql="UPDATE trip set bookmarkcnt= bookmarkcnt-1 WHERE contentid=?";
					
					if (bdto.getDeactivate().equals("FALSE")) {
						sql="UPDATE trip set bookmarkcnt= bookmarkcnt+1 WHERE contentid=?";				
					}
					ps=conn.prepareStatement(sql);
					ps.setInt(1, bdto.getContentid());				
					ps.executeUpdate();
					
				}
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return suc;
	}


}
