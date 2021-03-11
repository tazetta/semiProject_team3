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

import com.mvc.dto.BoardDTO;

public class BoardDAO {
	
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public BoardDAO() {
		
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resClose() {
			try {
				if(conn!=null) {
				conn.close();
				}
				if(ps!=null) {
					ps.close();
				}
				if(rs!=null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public HashMap<String, Object> list(int page) {
		
		HashMap<String,Object> map= new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page*pagePerCnt;
		int start = end-(pagePerCnt-1);
		String sql ="SELECT boardIdx,subject,bHit,reg_date,id FROM (" + 
				"    SELECT ROW_NUMBER() OVER(ORDER BY boardIdx DESC) AS rnum,boardIdx,subject,bHit,reg_date,id " + 
				"        FROM bbs" + 
				") WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBoardIdx(rs.getInt("boardIdx"));
				dto.setSubject(rs.getString("subject"));
				dto.setbHit(rs.getInt("bHit"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
			BoardDTO dto = new BoardDTO();
			int maxPage= getMaxPage(pagePerCnt);
			map.put("list",list);
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
		String sql = "SELECT COUNT(boardIdx) FROM bbs";
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
	
	
	public long write(BoardDTO dto) {
		String sql = "INSERT INTO bbs (boardIdx,subject,content,id)VALUES (bbs_seq.NEXTVAL,?,?,?)";
		long pk = 0;
		try {
			ps = conn.prepareStatement(sql,new String[] {"boardIdx"});
			ps.setString(1, dto.getSubject());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getId());
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				pk = rs.getLong(1);
				System.out.println("idx :"+pk);
				if(dto.getOriFileName()!=null) {
					sql = "INSERT INTO photo (fileIdx,oriFileName,newFileName,boardIdx)VALUES(photo_seq.NEXTVAL,?,?,?)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, dto.getOriFileName());
					ps.setString(2, dto.getNewFileName());
					ps.setLong(3, pk);
					ps.executeUpdate();					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return pk;
	}


	public BoardDTO detail(String boardIdx) {
		String sql="SELECT b.boardIdx, b.subject, b.content, b.bHit,b.id, p.oriFileName, p.newFileName "+ 
				"FROM bbs b, photo p WHERE b.boardIdx = p.boardIdx(+) AND b.boardIdx = ?";		
		BoardDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(boardIdx));
			rs=ps.executeQuery();
			if(rs.next()) {
				dto = new BoardDTO();
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setbHit(rs.getInt("bHit"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return dto;
	}

}
