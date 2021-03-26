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

public class ReportDAO {

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;



public ReportDAO() {		
		

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

	private int getMaxPage(int pagePerCnt, String deactivate, int type) {
		String sql = "SELECT count(r.bbsrepidx) FROM bbsrep r, bbs b WHERE r.boardidx=b.boardidx AND r.deactivate=? ";
		int max = 0;
		if (type==2) {
			sql = "SELECT count(r.commentrepidx) FROM commentrep r, bbs_comment b WHERE r.reidx=b.reidx AND r.deactivate=? ";
		}
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, deactivate);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	public RepDTO repReason(String Idx, int type, String reIdx) {
		RepDTO dto = null;
		String sql = "SELECT reason, deactivate, (SELECT reportcnt FROM bbs  WHERE boardIdx=? )AS repCnt,id FROM  bbsrep WHERE bbsrepidx=?";
		if (type==2) {
			sql = "SELECT reason, deactivate, (SELECT reportcnt FROM bbs_comment  WHERE reidx=? )AS repCnt,id FROM  commentrep WHERE commentrepidx=?";
		}
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, reIdx);
			ps.setString(2, Idx);
			rs = ps.executeQuery();
			if (rs.next()) {
				 dto = new RepDTO();
				dto.setReason(rs.getString("reason"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setRepCnt(rs.getInt("repCnt"));
				dto.setRid(rs.getString("id"));
				if(reIdx != null) {
					dto.setReIdx(Integer.parseInt(reIdx));					
				}
				if(Idx != null  ) {
					dto.setCommentRepIdx(Integer.parseInt(Idx));					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}


	public int updateYN(RepDTO dto, String managerid) {
		String midSql="'TRUE'";
		System.out.println(dto.getUpdateYN());
		if(dto.getUpdateYN()==null) {
			midSql="'FALSE'";
			dto.setUpdateYN("FALSE");
		}
		int suc = 0;
		try {
			if( dto.getUpdateYN().equals("FALSE")) {//허위 신고 처리 시 cnt 0으로 초기화
				String sql="UPDATE bbs SET reportcnt=0 WHERE boardidx=?";
				if(dto.getType().equals("2")) {
					sql = "UPDATE bbs_comment SET reportcnt=0 WHERE reidx=?";
				}
				System.out.println(dto.getBoardIdx());
				ps=conn.prepareStatement(sql);
				ps.setInt(1, dto.getBoardIdx());
				ps.executeUpdate();
				//멤버도 
				sql="SELECT id FROM bbs WHERE boardidx=?";
				if(dto.getType().equals("2")) {// 댓글일 때
					sql = "SELECT id FROM bbs_comment WHERE reidx=?";
				}
				ps= conn.prepareStatement(sql);
				ps.setInt(1, dto.getBoardIdx());
				rs = ps.executeQuery();
				String id = "";
				if(rs.next()) {
					id = rs.getString("id");
					System.out.println("아이디 : "+id);
				}
				if(dto.getDeactivate().equals("FALSE")) {
					// 멤버 cnt 까기 
					sql="UPDATE member SET reportcnt=reportcnt-1 WHERE id=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					int aa =ps.executeUpdate();
					System.out.println(id+"멤버 까임 ? :" + aa);
					////////
					
				}
			}
			//신고 내역 부터 처리
			String sql = "UPDATE bbsrep SET deactivate="+midSql+" , managerid=?,update_date=sysdate  WHERE bbsrepidx=?";
			if(dto.getType().equals("2")) {
				sql="UPDATE commentrep SET deactivate="+midSql+" , managerid=?,update_date=sysdate WHERE commentrepidx=?";
			}
			ps = conn.prepareStatement(sql);
			ps.setString(1, managerid);
			ps.setInt(2, dto.getBbsRepIdx());
			System.out.println(sql);
			suc = ps.executeUpdate();
			if (suc > 0) {
				//그리고 실제 게시물, 댓글 처리 
				sql = "UPDATE bbs SET deactivate=? WHERE boardidx=?";
				if(dto.getType().equals("2")) {
					sql = "UPDATE bbs_comment SET deactivate=? WHERE reidx=?";
				}
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getUpdateYN());
				ps.setInt(2, dto.getBoardIdx());
				suc = ps.executeUpdate();
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("블라인드 처리 확인 : " + suc);
		return suc;
	}

	public HashMap<String, Object> reportList(int page, String deactivate,String type) {
		HashMap<String, Object> map = null;
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		String sql = "SELECT boardidx,bid,reason,deactivate, bbsrepidx ,managerid,rid FROM" // 미처리 게시물 신고 순서대로 나열
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY r.bbsrepidx ASC) AS rnum "
				+ ",b.boardidx,b.id AS bid,r.reason,b.deactivate, r.bbsrepidx,r.managerid,r.id AS rid "
				+ "FROM bbsrep r, bbs b  WHERE r.boardidx=b.boardidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
		if(deactivate.equals("TRUE")) {
			sql = "SELECT boardidx,bid,reason,deactivate, bbsrepidx ,managerid,rid ,update_date FROM" // 처리 게시물 처리 순서대로 나열
					+ "(SELECT ROW_NUMBER() OVER(ORDER BY r.update_date DESC) AS rnum "
					+ ",b.boardidx,b.id AS bid,r.reason,b.deactivate, r.bbsrepidx,r.managerid,r.id AS rid ,r.update_date "
					+ "FROM bbsrep r, bbs b  WHERE r.boardidx=b.boardidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
		}
		if(type.equals("2")) {
			sql = "SELECT reidx,bid,reason,deactivate, commentrepidx ,managerid, boardIdx,rid FROM" +  // 미처리 댓글 신고 순서대로 나열
					"(SELECT ROW_NUMBER() OVER(ORDER BY r.commentrepidx ASC) AS rnum " + 
					",b.reidx,b.id AS bid,r.reason,b.deactivate, r.commentrepidx,r.managerid, b.boardIdx,r.id AS rid  " + 
					"FROM commentrep r, bbs_comment b  WHERE r.reidx=b.reidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
			if(deactivate.equals("TRUE")) {
				sql = "SELECT reidx,bid,reason,deactivate, commentrepidx ,managerid, boardIdx,rid,update_date FROM" +  // 처리 댓글 처리 순서대로 나열
						"(SELECT ROW_NUMBER() OVER(ORDER BY r.update_date DESC) AS rnum " + 
						",b.reidx,b.id AS bid,r.reason,b.deactivate, r.commentrepidx,r.managerid, b.boardIdx,r.id AS rid ,r.update_date " + 
						"FROM commentrep r, bbs_comment b  WHERE r.reidx=b.reidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
			}
		}		
		ArrayList<RepDTO> list = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, deactivate);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			list = new ArrayList<RepDTO>();
			while (rs.next()) {

				RepDTO dto = new RepDTO();
				
				if(type.equals("1")) {
					dto.setBbsRepIdx(rs.getInt("bbsrepidx"));				
					
				}else {
					dto.setReIdx(rs.getInt("reidx"));
					dto.setCommentRepIdx(rs.getInt("commentrepidx"));					
				}
				dto.setId(rs.getString("bid"));
				dto.setReason(rs.getString("reason"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setManagerId(rs.getString("managerid"));
				dto.setBoardIdx(rs.getInt("boardIdx"));
				dto.setRid(rs.getString("rid"));
				
				System.out.println("처리자 : "+rs.getString("managerid"));
				list.add(dto);
			}			
			map = new HashMap<String, Object>();
			int maxPage = getMaxPage(pagePerCnt,deactivate,Integer.parseInt(type));
			map.put("list", list);
			map.put("maxPage", maxPage);
			System.out.println("maxPage: " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public boolean tasked(RepDTO dto) {
		boolean suc = false;
		String sql = "SELECT * FROM bbsrep WHERE boardIDX = ? AND deactivate = 'FALSE'";
		if(dto.getType().equals("2")) {
			 sql = "SELECT * FROM commentrep WHERE reIdx = ? AND deactivate = 'FALSE'";
		}
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, dto.getBoardIdx());
			rs = ps.executeQuery();
			if(rs.next()) {
				suc=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return suc;
	}


}
