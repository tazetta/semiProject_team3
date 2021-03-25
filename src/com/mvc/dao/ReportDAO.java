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

	/*
	 * // public TripDTO tripDetail(String conIdx) { // TripDTO dto = null; // //
	 * 지역코드 가져와야함. // // try { // // String sql =
	 * "SELECT title,reg_date,firstimage,deactivate,overview,LATITUDE,LONGITUDE" //
	 * +
	 * "    ,(SELECT name FROM area WHERE areacode=(SELECT areacode FROM trip WHERE contentid=?)) AS area"
	 * // +
	 * "    ,(SELECT COUNT(myidx) FROM bookmark WHERE contentid=? AND deactivate='FALSE' AND type=1) AS bookmark"
	 * // + "    FROM trip WHERE contentid=? AND deactivate='FALSE'"; // ps =
	 * conn.prepareStatement(sql); // ps.setString(1, conIdx); // ps.setString(2,
	 * conIdx); // ps.setString(3, conIdx); // rs = ps.executeQuery(); // if
	 * (rs.next()) { // System.out.println(rs.getString("firstimage")); // dto = new
	 * TripDTO(); // dto.setTitle(rs.getString("title")); //
	 * dto.setReg_date(rs.getDate("reg_date")); //
	 * dto.setFirstImage(rs.getString("firstimage")); //
	 * dto.setDeactivate(rs.getString("deactivate")); //
	 * dto.setArea(rs.getString("area")); // dto.setBookmark(rs.getInt("bookmark"));
	 * // dto.setOverview(rs.getString("overview")); //
	 * dto.setLatitude(rs.getString("LATITUDE")); //
	 * dto.setLongitude(rs.getString("LONGITUDE")); // } // // } catch (Exception e)
	 * { // e.printStackTrace(); // } // return dto; // } public TripDTO
	 * tripDetail(String conIdx) { TripDTO dto = null; // 지역코드 가져와야함.
	 * 
	 * try {
	 * 
	 * String sql =
	 * "SELECT title,reg_date,firstimage,deactivate,overview,LATITUDE,LONGITUDE" +
	 * "    ,(SELECT name FROM area WHERE areacode=(SELECT areacode FROM trip WHERE contentid=?)) AS area"
	 * + "    ,bookmarkCnt" +
	 * "    FROM trip WHERE contentid=? AND deactivate='FALSE'"; ps =
	 * conn.prepareStatement(sql); ps.setString(1, conIdx); ps.setString(2, conIdx);
	 * rs = ps.executeQuery(); if (rs.next()) {
	 * System.out.println(rs.getString("firstimage")); dto = new TripDTO();
	 * dto.setTitle(rs.getString("title")); dto.setReg_date(rs.getDate("reg_date"));
	 * dto.setFirstImage(rs.getString("firstimage"));
	 * dto.setDeactivate(rs.getString("deactivate"));
	 * dto.setArea(rs.getString("area")); dto.setBookmark(rs.getInt("bookmarkCnt"));
	 * dto.setOverview(rs.getString("overview"));
	 * dto.setLatitude(rs.getString("LATITUDE"));
	 * dto.setLongitude(rs.getString("LONGITUDE")); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return dto; }
	 * 
	 * public BookmarkDTO bookmark(String conIdx, String id) { // id 받아와야함.
	 * System.out.println(conIdx); String sql =
	 * "SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=1"; BookmarkDTO
	 * dto = null;
	 * 
	 * try { ps = conn.prepareStatement(sql); ps.setString(1, conIdx);
	 * ps.setString(2, id); rs = ps.executeQuery(); while (rs.next()) { dto = new
	 * BookmarkDTO(); dto.setMyidx(rs.getInt("myidx"));
	 * dto.setContentid(rs.getInt("contentid")); dto.setId(rs.getString("id"));
	 * dto.setReg_date(rs.getDate("reg_date")); dto.setType(rs.getInt("type"));
	 * dto.setDeactivate(rs.getString("deactivate"));
	 * 
	 * } } catch (SQLException e) { e.printStackTrace(); } System.out.println(dto);
	 * return dto; }
	 * 
	 * public BookmarkDTO visit(String conIdx, String id) { // id 받아와야함. String sql
	 * = "SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=2"; BookmarkDTO
	 * dto = null;
	 * 
	 * try { ps = conn.prepareStatement(sql); ps.setString(1, conIdx);
	 * ps.setString(2, id); rs = ps.executeQuery(); while (rs.next()) { dto = new
	 * BookmarkDTO(); dto.setMyidx(rs.getInt("myidx"));
	 * dto.setContentid(rs.getInt("contentid")); dto.setId(rs.getString("id"));
	 * dto.setReg_date(rs.getDate("reg_date")); dto.setType(rs.getInt("type"));
	 * dto.setDeactivate(rs.getString("deactivate"));
	 * 
	 * } } catch (SQLException e) { e.printStackTrace(); } System.out.println(dto);
	 * return dto; }
	 * 
	 * public int addDel(BookmarkDTO bdto) { int suc = 0; String sql =
	 * "INSERT INTO bookmark (myIdx, contentId, id, type) VALUES (bookmark_seq.NEXTVAL,?,?,?)"
	 * ; try { if (bdto.getMyidx() > 0) { System.out.println("업데이트"); sql =
	 * "UPDATE bookmark SET deactivate='FALSE' WHERE myidx=?  "; if
	 * (bdto.getDeactivate().equals("FALSE")) { sql =
	 * "UPDATE bookmark SET deactivate='TRUE' WHERE myidx=? "; } ps =
	 * conn.prepareStatement(sql); ps.setInt(1, bdto.getMyidx()); } else { ps =
	 * conn.prepareStatement(sql); ps.setInt(1, bdto.getContentid());
	 * ps.setString(2, bdto.getId()); ps.setInt(3, bdto.getType());
	 * 
	 * } suc = ps.executeUpdate(); System.out.println("추가,수정 : " + suc);
	 * 
	 * if(bdto.getType()==1) {
	 * sql="UPDATE trip set bookmarkcnt= bookmarkcnt+1 WHERE contentid=?";
	 * 
	 * if (bdto.getDeactivate().equals("FALSE")) {
	 * sql="UPDATE trip set bookmarkcnt= bookmarkcnt-1 WHERE contentid=?"; }
	 * ps=conn.prepareStatement(sql); ps.setInt(1, bdto.getContentid());
	 * ps.executeUpdate();
	 * 
	 * }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return suc; }
	 */
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

//	public String repCnt(String boardIdx, int type) {
//		String repCnt = "";
//		String sql = "SELECT count(bbsrepidx) AS repCnt FROM bbsrep  WHERE boardIdx=?";
//		
//		if(type==2) {
//			sql="SELECT count(commentrepidx) AS repCnt FROM commentrep  WHERE reIdx=?";
//		}
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, boardIdx);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				repCnt = rs.getString("repCnt");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return repCnt;
//	}

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

	public HashMap<String, Object> reportComment(int page, String deactivate,String type) {
		HashMap<String, Object> map = null;
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		String sql = "SELECT boardidx,bid,reason,deactivate, bbsrepidx ,managerid,rid FROM"
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY r.bbsrepidx ASC) AS rnum "
				+ ",b.boardidx,b.id AS bid,r.reason,b.deactivate, r.bbsrepidx,r.managerid,r.id AS rid "
				+ "FROM bbsrep r, bbs b  WHERE r.boardidx=b.boardidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
		if(deactivate.equals("TRUE")) {
			sql = "SELECT boardidx,bid,reason,deactivate, bbsrepidx ,managerid,rid ,update_date FROM"
					+ "(SELECT ROW_NUMBER() OVER(ORDER BY r.update_date DESC) AS rnum "
					+ ",b.boardidx,b.id AS bid,r.reason,b.deactivate, r.bbsrepidx,r.managerid,r.id AS rid ,r.update_date "
					+ "FROM bbsrep r, bbs b  WHERE r.boardidx=b.boardidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
		}
		if(type.equals("2")) {
			sql = "SELECT reidx,bid,reason,deactivate, commentrepidx ,managerid, boardIdx,rid FROM" + 
					"(SELECT ROW_NUMBER() OVER(ORDER BY r.commentrepidx ASC) AS rnum " + 
					",b.reidx,b.id AS bid,r.reason,b.deactivate, r.commentrepidx,r.managerid, b.boardIdx,r.id AS rid  " + 
					"FROM commentrep r, bbs_comment b  WHERE r.reidx=b.reidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
			if(deactivate.equals("TRUE")) {
				sql = "SELECT reidx,bid,reason,deactivate, commentrepidx ,managerid, boardIdx,rid,update_date FROM" + 
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

//	public HashMap<String, Object> reportBBS(int page, String deactivate) {
//		HashMap<String, Object> map = null;
//		int pagePerCnt = 10;
//		int end = page * pagePerCnt;
//		int start = end - (pagePerCnt - 1);
//		String sql = "SELECT boardidx,id,reason,deactivate, bbsrepidx ,managerid FROM"
//				+ "(SELECT ROW_NUMBER() OVER(ORDER BY bbsrepidx DESC) AS rnum "
//				+ ",b.boardidx,b.id,r.reason,b.deactivate, r.bbsrepidx,r.managerid "
//				+ "FROM bbsrep r, bbs b  WHERE r.boardidx=b.boardidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
//		ArrayList<RepDTO> list = null;
//
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, deactivate);
//			ps.setInt(2, start);
//			ps.setInt(3, end);
//			rs = ps.executeQuery();
//			list = new ArrayList<RepDTO>();
//			while (rs.next()) {
//
//				RepDTO dto = new RepDTO();
//				dto.setBoardIdx(rs.getInt("boardIdx"));
//				dto.setId(rs.getString("id"));
//				dto.setReason(rs.getString("reason"));
//				dto.setDeactivate(rs.getString("deactivate"));
//				dto.setBbsRepIdx(rs.getInt("bbsrepidx"));
//				dto.setManagerId(rs.getString("managerid"));
//				System.out.println("처리자 : "+rs.getString("managerid"));
//				list.add(dto);
//			}
//			map = new HashMap<String, Object>();
//			int type=1;
//			int maxPage = getMaxPage(pagePerCnt,deactivate,type);
//			map.put("list", list);
//			map.put("maxPage", maxPage);
//			System.out.println("maxPage: " + maxPage);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return map;
//	}


}
