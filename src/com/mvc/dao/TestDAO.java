package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.BoardDTO;
import com.mvc.dto.RepDTO;
import com.mvc.dto.TestBookDTO;
import com.mvc.dto.TripDTO;



public class TestDAO {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps  = null;

	public TestDAO() {		
		
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
			if(conn!=null) {conn.close();}
			if(rs!=null) {rs.close();}
			if(ps!=null) {ps.close();}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public TripDTO tripDetail(String conIdx) {
		TripDTO dto = null;
		//지역코드 가져와야함.
		
		try {
			
			String sql = "SELECT title,reg_date,firstimage,deactivate,overview,LATITUDE,LONGITUDE" + 
					"    ,(SELECT name FROM area WHERE areacode=(SELECT areacode FROM trip WHERE contentid=?)) AS area" + 
					"    ,(SELECT COUNT(myidx) FROM bookmark WHERE contentid=? AND deactivate='FALSE' AND type=1) AS bookmark" + 
					"    FROM trip WHERE contentid=? AND deactivate='FALSE'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, conIdx);
			ps.setString(3, conIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getString("firstimage"));
				dto = new TripDTO();
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setFirstImage(rs.getString("firstimage"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setArea(rs.getString("area"));
				dto.setBookmark(rs.getInt("bookmark"));
				dto.setOverview(rs.getString("overview"));
				dto.setLatitude(rs.getString("LATITUDE"));
				dto.setLongitude(rs.getString("LONGITUDE"));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public TestBookDTO bookmark(String conIdx, String id) {
		//id 받아와야함.
		System.out.println(conIdx);
		String sql ="SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=1";
		TestBookDTO dto =null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new TestBookDTO();
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

	public TestBookDTO visit(String conIdx, String id) {
		//id 받아와야함.
		String sql ="SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=2";
		TestBookDTO dto =null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new TestBookDTO();
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



	public int addDel(TestBookDTO bdto) {
				int suc = 0;
				String sql = "INSERT INTO bookmark (myIdx, contentId, id, type) VALUES (bookmark_seq.NEXTVAL,?,?,?)";
				try {
					if(bdto.getMyidx()>0) {
						System.out.println("업데이트");
						 sql = "UPDATE bookmark SET deactivate='FALSE' WHERE myidx=?  ";
						if(bdto.getDeactivate().equals("FALSE")) {
							sql = "UPDATE bookmark SET deactivate='TRUE' WHERE myidx=? ";				
						}			
						ps = conn.prepareStatement(sql);
						ps.setInt(1, bdto.getMyidx());
					}else {
						ps = conn.prepareStatement(sql);
						ps.setInt(1, bdto.getContentid());;
						ps.setString(2, bdto.getId());;
						ps.setInt(3, bdto.getType());
					}
					suc=ps.executeUpdate();
					
					System.out.println("추가,수정 : " + suc);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				return suc;
	}

	public void reportBBS() {
//		String sql="SELECT boardidx,id,deactivate FROM bbs WHERE reportcnt>0 AND deactivate='FALSE'";
		String sql = "SELECT boardidx,reason FROM bbsrep";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			ps= conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				RepDTO dto = new RepDTO();
				dto.setBbsRepIdx(rs.getInt(""));
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


}
