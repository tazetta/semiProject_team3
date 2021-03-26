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

		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		String sql = "SELECT reg_date, id, name, phone, email FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) " + "AS rnum, reg_date, id, name, phone, email "
				+ "FROM member WHERE id NOT IN ('admin') AND name != '탈a퇴#회@원') WHERE rnum BETWEEN ? AND ?";

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
			int type = 1;
			int maxPage = getMaxPage(pagePerCnt, type);
			map.put("memberList", memberList);
			map.put("maxPage", maxPage);
			System.out.println("maxPage: " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int getMaxPage(int pagePerCnt, int type) {
		String sql = "SELECT COUNT(id) FROM member WHERE id NOT IN ('admin')";
		int max = 0;
		if (type == 2) {
			sql = "SELECT COUNT(id) FROM member WHERE withdraw='TRUE'";
		}
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return max;
	}

	public MemberListDTO memberDetail(String id) {

		MemberListDTO dto = null;
		boolean success = false;
		String sql = "SELECT reg_date, id, name, phone, email, withdraw, reportcnt, update_date, blackcnt,  "
				+ "(SELECT count(blackidx) FROM blacklist WHERE id=? AND blackstatus='TRUE') AS staus_cnt "
				+ "FROM member WHERE id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, id);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("id : " + id);
			if (rs.next()) {
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
			if (rs.getInt("staus_cnt") > 0) {
				dto.setBlackstatus("TRUE");
				success = true;
			} else {
				dto.setBlackstatus("FALSE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		System.out.println("상세보기&블랙 성공여부 :" + success);
		return dto;
	}

	public HashMap<String, Object> memberDelList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);

		String sql = "SELECT reg_date, withdraw, id, name, phone, email FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) "
				+ "AS rnum, reg_date, withdraw,  id, name, phone, email "
				+ "FROM member WHERE withdraw='TRUE' AND name != '탈a퇴#회@원' " + ") WHERE rnum BETWEEN ? AND ?";

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
			int maxPage = Del_getMaxPage(pagePerCnt);
			map.put("memberDelList", memberDelList);
			map.put("maxPage", maxPage);
			System.out.println("maxPage: " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int Del_getMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(id) FROM member WHERE withdraw='TRUE'";
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
		} finally {
			resClose();
		}
		return max;
	}

	public boolean memberDraw(String id) {

		String sql = "UPDATE member SET pw='' ,name='탈a퇴#회@원', phone='', email='',withdraw='TRUE'  WHERE id=?";
		boolean success = false;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			if (ps.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		System.out.println("회원 삭제여부 :" + success);
		return success;
	}

	public boolean memberRestore(String id) {

		String sql = "UPDATE member SET withdraw='FALSE' WHERE id=?";
		boolean success = false;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			if (ps.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		System.out.println("회원 복구 성공여부 :" + success);
		return success;
	}

	public HashMap<String, Object> memberSearch(int page, String searchType, String memberKeyword) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		/*
		 * String sql = "SELECT reg_date, id, name, phone, email FROM (" +
		 * "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) " +
		 * "AS rnum, reg_date, id, name, phone, email " +
		 * "FROM member WHERE id NOT IN ('admin') AND withdraw='FALSE' AND " +
		 * searchType + "=?) WHERE rnum BETWEEN ? AND ?";
		 */
		
		String sql = "SELECT reg_date, id, name, phone, email FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) " + "AS rnum, reg_date, id, name, phone, email "
				+ "FROM member WHERE id NOT IN ('admin') AND name != '탈a퇴#회@원' AND " + searchType + "=?) WHERE rnum BETWEEN ? AND ?";
		ArrayList<MemberListDTO> memberSearchList = new ArrayList<MemberListDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, memberKeyword);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberListDTO dto = new MemberListDTO();
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				memberSearchList.add(dto);
			}
			int maxPage = search_getMaxPage(pagePerCnt, searchType, memberKeyword);
			map.put("memberSearchList", memberSearchList);
			map.put("maxPage", maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int search_getMaxPage(int pagePerCnt, String searchType, String memberKeyword) {
		String sql = "SELECT COUNT(id) FROM member WHERE " + searchType + "= ?";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, memberKeyword);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return max;
	}

	public HashMap<String, Object> memberBlackList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		String sql = "SELECT blackidx, id, reason, reg_date, managerid, blackstatus FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY blackstatus DESC, blackidx DESC) AS rnum, blackidx, id, reason, reg_date, managerid, blackstatus "
				+ "FROM blacklist where ROWID IN (select MAX(ROWID) from blacklist group by id)) WHERE rnum BETWEEN ? AND ?";

		ArrayList<MemberListDTO> memberBlackList = new ArrayList<MemberListDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberListDTO dto = new MemberListDTO();
				dto.setBlackidx(rs.getInt("blackidx"));
				dto.setId(rs.getString("id"));
				dto.setReason(rs.getString("reason"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setManagerid(rs.getString("managerid"));
				dto.setBlackstatus(rs.getString("blackstatus"));
				memberBlackList.add(dto);
			}
			int maxPage = black_getMaxPage(pagePerCnt);
			map.put("memberBlackList", memberBlackList);
			map.put("maxPage", maxPage);
			System.out.println("maxPage: " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int black_getMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(blackidx) FROM blacklist";
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
		} finally {
			resClose();
		}
		return max;
	}

	public boolean memberBlackAdd(MemberListDTO dto) {
		String sql = "INSERT INTO blacklist (blackidx,id,reason,managerid) VALUES (black_seq.NEXTVAL,?,?,?)";
		boolean success = false;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getReason());
			ps.setString(3, dto.getManagerid());
			if (ps.executeUpdate() > 0) { // 블랙 카운트 증가시키고
				String black_sql = "UPDATE member SET blackcnt=blackcnt+1 WHERE id=?";
				ps = conn.prepareStatement(black_sql);
				ps.setString(1, dto.getId());

//				int cnt = 0;
//				if (ps.executeUpdate() > 0) {
//					cnt += 1;
//					System.out.println(cnt);
//				}
				ps.executeUpdate();
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		System.out.println("블랙리스트 추가 성공여부 :" + success);
		return success;
	}

	public MemberListDTO memberBlackDetail(String blackidx) {

		MemberListDTO dto = null;

		String sql = "select b.blackidx, b.reg_date, b.id, b.managerid, b.reason, b.blackstatus ,m.reportcnt, m.blackcnt, m.name, m.update_date"
				+ " from blacklist b, member m where b.id=m.id AND b.blackidx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, blackidx);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("rs : " + rs);
			if (rs.next()) {
				dto = new MemberListDTO();
				dto.setBlackidx(rs.getInt("blackidx"));
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
		} finally {
			resClose();
		}
		return dto;
	}
	
	public ArrayList<MemberListDTO> reason(String blackidx) {
		ArrayList<MemberListDTO> reason = new ArrayList<MemberListDTO>();
		MemberListDTO dto = null;
		
		String sql = "select managerid, reason, reg_date from blacklist where id = (select id from blacklist where blackidx=?) ORDER BY reg_date DESC";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, blackidx);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new MemberListDTO();
				dto.setManagerid(rs.getString("managerid"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setReason(rs.getString("reason"));
				reason.add(dto);
			}
			}catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resClose();
			}
		System.out.println(reason);
		return reason;
	}

	public boolean memberBlackDel(String blackidx) {

		String sql = "UPDATE blacklist SET blackstatus='FALSE' WHERE blackidx=?";
		boolean success = false;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, blackidx);
			if (ps.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		System.out.println("블랙리스트 삭제여부 :" + success);
		return success;
	}

	public MemberListDTO memberDelDetail(String id) {
		MemberListDTO dto = null;
		String sql = "SELECT reg_date, id, name, phone, email, withdraw, reportcnt, update_date, blackcnt FROM "
				+ "member WHERE id=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			System.out.println("쿼리 실행");
			rs = ps.executeQuery();
			System.out.println("rs : " + rs);
			if (rs.next()) {
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
		} finally {
			resClose();
		}
		return dto;
	}

	


}