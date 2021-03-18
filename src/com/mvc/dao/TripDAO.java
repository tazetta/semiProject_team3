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

import com.mvc.dto.AreaDTO;
import com.mvc.dto.CityDTO;
import com.mvc.dto.ContentDTO;
import com.mvc.dto.TripDTO;

public class TripDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public TripDAO() {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ContentDTO> contentList() {
		ArrayList<ContentDTO> list = new ArrayList<ContentDTO>();
		ContentDTO dto = null;
		String sql = "SELECT contentCode,name FROM contenttype";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new ContentDTO();
				dto.setContentCode(rs.getString("contentCode"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<AreaDTO> areaList() {
		ArrayList<AreaDTO> list = new ArrayList<AreaDTO>();
		AreaDTO dto = null;
		String sql = "SELECT areaCode,name FROM area";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new AreaDTO();
				dto.setAreaCode(rs.getString("areaCode"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<CityDTO> cityList(String areaCode) {
		ArrayList<CityDTO> list = new ArrayList<CityDTO>();
		CityDTO dto = null;
		try {
			if (!areaCode.equals("0")) {
				String sql = "SELECT cityCode,name, areaCode FROM city WHERE areacode=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, areaCode);
				rs = ps.executeQuery();
				while (rs.next()) {
					dto = new CityDTO();
					dto.setCityCode(rs.getInt("cityCode"));
					dto.setName(rs.getString("name"));
					list.add(dto);
				}
			}	else if (areaCode.equals("0")) { // tripInsetrInformation으로 요청이 올 때
				String sql = "SELECT c.cityCode, c.name, a.name, a.areaCode FROM city c, area a WHERE c.areacode = a.areacode ORDER BY c.citycode";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					dto = new CityDTO();
					dto.setCityCode(rs.getInt(1));
					dto.setName(rs.getString(2));
					dto.setAreaName(rs.getString(3));
					dto.setAreaCode(rs.getString(4));
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private void chkCheckBox(String[] localCode) throws SQLException {
		if (localCode.length == 1) {
			ps.setString(1, localCode[0]);
		} else if (localCode.length == 2) {
			ps.setString(1, localCode[0]);
			ps.setString(2, localCode[1]);
		} else if (localCode.length == 3) {
			ps.setString(1, localCode[0]);
			ps.setString(2, localCode[1]);
			ps.setString(3, localCode[2]);
		}
	}

	public HashMap<String, Object> resultList(int page, String nav, String[] localCode, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		int maxPage = 0;
		System.out.println("end : " + end + " / start : " + start);

		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
		// 체크박스가 선택된 수만큼 ? 생성
		String inSQL = " IN(";
		for (int i = 1; i <= localCode.length; i++) {
			if (i == localCode.length) {
				inSQL += "?)";
			} else {
				inSQL += "?,";
			}
		}
		// type이 theme일 때
		String insertSQL = " areaCode" + inSQL + " AND contentCode=?";
		if (type.equals("area")) { // type이 area일 때
			insertSQL = " cityCode" + inSQL + " AND areaCode = ?";
		}
		String sql = "SELECT contentId,areaCode,contentCode,firstImage,bookmarkCnt, title, reg_date FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY bookmarkCnt DESC) AS rnum, "
				+ "contentId,areaCode,contentCode,bookmarkCnt,firstImage,title,reg_date FROM trip WHERE " + insertSQL + " AND deactivate = 'FALSE'"
				+ ") WHERE rnum BETWEEN ? AND ?";
		try {
			ps = conn.prepareStatement(sql);
			chkCheckBox(localCode);
			ps.setString(localCode.length + 1, nav);
			ps.setInt(localCode.length + 2, start);
			ps.setInt(localCode.length + 3, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				TripDTO dto = new TripDTO();
				dto.setContentId(rs.getInt("contentId"));
				dto.setAreaCode(rs.getString("areaCode"));
				dto.setContentCode(rs.getString("contentCode"));
				dto.setFirstImage(rs.getString("firstImage"));
				dto.setBookmarkCnt(rs.getInt("bookmarkCnt"));
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getDate("reg_date"));
				list.add(dto);
			}
			maxPage = getMaxPage(nav, localCode, pagePerCnt, type);
			System.out.println("max page : " + maxPage);
			map.put("list", list);
			map.put("maxPage", maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int getMaxPage(String nav, String[] localCode, int pagePerCnt, String type) {
		int maxPage = 0;
		try {
			String inSQL = " IN(";
			for (int i = 1; i <= localCode.length; i++) {
				if (i == localCode.length) {
					inSQL += "?)";
				} else {
					inSQL += "?,";
				}
			}
			String insertSQL = " areaCode" + inSQL + " AND contentCode=?";
			if (type.equals("area")) { // type이 area일 때
				insertSQL = " cityCode" + inSQL + " AND areaCode = ?";
			}
			String sql = "SELECT COUNT(contentId) FROM trip WHERE " + insertSQL + " AND deactivate = 'FALSE'";
			ps = conn.prepareStatement(sql);
			chkCheckBox(localCode);
			ps.setString(localCode.length + 1, nav);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				maxPage += (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxPage;
	}
	
//	public HashMap<String, Object> search(int page, String keyword, String searchType, String alignType) {
//		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
//		TripDTO dto = null;
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		// 페이징
//		int pagePerCnt = 10;
//		int end = page * pagePerCnt;
//		int start = end - (pagePerCnt - 1);
//		int maxPage = 0;
//		
//		// 검색어
//		String addKeyword = "%"+keyword+"%";
//		String sql = "SELECT contentId,firstImage, title, bookmarkCnt, reg_date FROM ("
//				+ "SELECT ROW_NUMBER() OVER(ORDER BY "+alignType+" DESC) AS rnum, "
//				+ "contentId,areaCode,contentCode,bookmarkCnt,firstImage,title,reg_date FROM trip WHERE "+searchType+" LIKE ? AND deactivate='FALSE'"
//				+ ") WHERE rnum BETWEEN ? AND ?";
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, addKeyword);
//			ps.setInt(2, start);
//			ps.setInt(3, end);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				dto = new TripDTO();
//				dto.setContentId(rs.getInt("contentId"));
//				dto.setFirstImage(rs.getString("firstImage"));
//				dto.setTitle(rs.getString("title"));
//				dto.setBookmarkCnt(rs.getInt("bookmarkCnt"));
//				dto.setReg_date(rs.getDate("reg_date"));
//				list.add(dto);
//			}
//			maxPage = getSearchMaxPage(pagePerCnt, keyword, searchType);
//			map.put("maxPage", maxPage);
//			map.put("list", list);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			resClose();
//		}
//		return map;
//	}
	
	public HashMap<String, Object> search(int page, String keyword, String searchType, String alignType) {
		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
		TripDTO dto = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 페이징
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		int maxPage = 0;
		
		// 검색어
		String addKeyword = "%"+keyword+"%";
		String sql = "SELECT contentId, firstImage, title, overview, address FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY "+alignType+" DESC) AS rnum, "
				+ "contentId,firstImage,title, overview, address FROM trip WHERE "+searchType+" LIKE ? AND deactivate='FALSE'"
				+ ") WHERE rnum BETWEEN ? AND ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, addKeyword);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new TripDTO();
				dto.setContentId(rs.getInt("contentId"));
				dto.setFirstImage(rs.getString("firstImage"));
				dto.setTitle(rs.getString("title"));
				dto.setOverview(rs.getString("overview"));
				dto.setAddress(rs.getString("address"));
				list.add(dto);
			}
			maxPage = getSearchMaxPage(pagePerCnt, keyword, searchType);
			map.put("maxPage", maxPage);
			map.put("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}

	private int getSearchMaxPage(int pagePerCnt, String keyword, String type) {
		int maxPage = 0;
		try {
			String addKeyword = "%"+keyword+"%";
			String sql = "SELECT COUNT(contentId) FROM trip WHERE "+type+" LIKE ? AND deactivate='FALSE'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, addKeyword);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				maxPage += (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxPage;
	}

	/*인기 여행지 사진 TOP3*/
	public ArrayList<TripDTO> popularImage() {
		String sql ="SELECT firstimage,title  FROM (SELECT firstimage,title, bookmarkcnt  FROM trip ORDER BY trip.bookmarkcnt DESC)WHERE rownum <=3 ORDER BY rownum";
		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			TripDTO dto  = null;
			while(rs.next()) {
				dto = new TripDTO();
				dto.setFirstImage(rs.getString("firstimage"));
				dto.setTitle(rs.getString("title"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return list;
	}

}
