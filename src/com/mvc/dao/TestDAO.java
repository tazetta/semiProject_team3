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
import com.mvc.dto.TestBookDTO;
import com.mvc.dto.TripDTO;

public class TestDAO {

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;



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
					+ "    ,(SELECT COUNT(myidx) FROM bookmark WHERE contentid=? AND deactivate='FALSE' AND type=1) AS bookmark"
					+ "    FROM trip WHERE contentid=? AND deactivate='FALSE'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, conIdx);
			ps.setString(3, conIdx);
			rs = ps.executeQuery();
			if (rs.next()) {
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
		// id 받아와야함.
		System.out.println(conIdx);
		String sql = "SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=1";
		TestBookDTO dto = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
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
		// id 받아와야함.
		String sql = "SELECT * FROM bookmark WHERE contentid=? AND id=? AND type=2";
		TestBookDTO dto = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, conIdx);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
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
			if (bdto.getMyidx() > 0) {
				System.out.println("업데이트");
				sql = "UPDATE bookmark SET deactivate='FALSE' WHERE myidx=?  ";
				if (bdto.getDeactivate().equals("FALSE")) {
					sql = "UPDATE bookmark SET deactivate='TRUE' WHERE myidx=? ";
				}
				ps = conn.prepareStatement(sql);
				ps.setInt(1, bdto.getMyidx());
			} else {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, bdto.getContentid());
				;
				ps.setString(2, bdto.getId());
				;
				ps.setInt(3, bdto.getType());
			}
			suc = ps.executeUpdate();

			System.out.println("추가,수정 : " + suc);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return suc;
	}

	public HashMap<String, Object> reportBBS(int page, String deactivate) {
		HashMap<String, Object> map = null;
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		String sql = "SELECT boardidx,id,reason,deactivate, bbsrepidx ,managerid FROM"
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY bbsrepidx DESC) AS rnum "
				+ ",b.boardidx,b.id,r.reason,b.deactivate, r.bbsrepidx,r.managerid "
				+ "FROM bbsrep r, bbs b  WHERE r.boardidx=b.boardidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
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
				dto.setBoardIdx(rs.getInt("boardIdx"));
				dto.setId(rs.getString("id"));
				dto.setReason(rs.getString("reason"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setBbsRepIdx(rs.getInt("bbsrepidx"));
				dto.setManagerId(rs.getString("managerid"));
				System.out.println("처리자 : "+rs.getString("managerid"));
				list.add(dto);
			}
			map = new HashMap<String, Object>();
			int maxPage = getMaxPage(pagePerCnt,deactivate);
			map.put("list", list);
			map.put("maxPage", maxPage);
			System.out.println("maxPage: " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	private int getMaxPage(int pagePerCnt, String deactivate) {
		String sql = "SELECT count(r.bbsrepidx) FROM bbsrep r, bbs b WHERE r.boardidx=b.boardidx AND r.deactivate=? ";
		int max = 0;
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

	public String repReason(String bbsRepIdx) {
		String reason = "";
		String sql = "SELECT reason FROM  bbsrep WHERE bbsrepidx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bbsRepIdx);
			rs = ps.executeQuery();
			if (rs.next()) {
				reason = rs.getString("reason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reason;
	}

	public String repCnt(String boardIdx) {
		String repCnt = "";
		String sql = "SELECT count(bbsrepidx) AS repCnt FROM bbsrep  WHERE boardIdx=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, boardIdx);
			rs = ps.executeQuery();
			if (rs.next()) {
				repCnt = rs.getString("repCnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return repCnt;
	}

	public int updateYN(String updateYN, String boardIdx, String bbsRepIdx) {
		String sql = "UPDATE bbsrep SET deactivate='TRUE' , managerid='admin' WHERE bbsrepidx=?";
		int suc = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bbsRepIdx);
			suc = ps.executeUpdate();
			if (suc > 0) {
				sql = "UPDATE bbs SET deactivate=? WHERE boardidx=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, updateYN);
				ps.setString(2, boardIdx);
				suc = ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("블라인드 처리 확인 : " + suc);
		return suc;
	}

	public HashMap<String, Object> reportComment(int page, String deactivate) {
		HashMap<String, Object> map = null;
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		String sql = "SELECT reidx,id,reason,deactivate, commentrepidx ,managerid FROM" + 
				"(SELECT ROW_NUMBER() OVER(ORDER BY r.reidx DESC) AS rnum " + 
				",b.reidx,b.id,r.reason,b.deactivate, r.commentrepidx,r.managerid " + 
				"FROM commentrep r, bbs_comment b  WHERE r.reidx=b.reidx AND  r.DEACTIVATE=?) WHERE rnum BETWEEN ? AND ?";
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
				dto.setReIdx(rs.getInt("reidx"));
				dto.setId(rs.getString("id"));
				dto.setReason(rs.getString("reason"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setCommentRepIdx(rs.getInt("commentrepidx"));
				dto.setManagerId(rs.getString("managerid"));
				System.out.println("처리자 : "+rs.getString("managerid"));
				list.add(dto);
			}
			map = new HashMap<String, Object>();
			int maxPage = getMaxPage(pagePerCnt,deactivate);
			map.put("list", list);
			map.put("maxPage", maxPage);
			System.out.println("maxPage: " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

}
