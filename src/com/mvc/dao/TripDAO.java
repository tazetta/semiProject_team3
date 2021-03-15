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
import com.mvc.dto.LargeDTO;
import com.mvc.dto.MediumDTO;
import com.mvc.dto.SmallDTO;
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
				+ "contentId,areaCode,contentCode,bookmarkCnt,firstImage,title,reg_date FROM trip WHERE " + insertSQL
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
			String sql = "SELECT COUNT(contentId) FROM trip WHERE " + insertSQL;
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

	public boolean insert(TripDTO dto) {
		boolean success = false;
		String sql = "INSERT INTO trip(managerId,contentId,firstImage,latitude,longitude,address,title,"
				+ "contentCode,mediumCode,smallCode,areaCode,cityCode,largeIdx,overview) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getManagerId());
			ps.setInt(2, dto.getContentId());
			ps.setString(3, dto.getFirstImage());
			ps.setString(4, dto.getLatitude());
			ps.setString(5, dto.getLongitude());
			ps.setString(6, dto.getAddress());
			ps.setString(7, dto.getTitle());
			ps.setString(8, dto.getContentCode());
			ps.setString(9, dto.getMediumCode());
			ps.setString(10, dto.getSmallCode());
			ps.setString(11, dto.getAreaCode());
			ps.setString(12, dto.getCityCode());
			ps.setString(13, dto.getLargeIdx());
			ps.setString(14, dto.getOverview());
			if (ps.executeUpdate() > 0) {
				success = true;
			}
			System.out.println("insert success : " + success);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}

	public boolean tripInsertOverlay(String contentId) {
		boolean success = false;
		String sql = "SELECT contentId FROM trip WHERE contentId=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, contentId);
			rs = ps.executeQuery();
			success = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}

		return !success;
	}

	public ArrayList<LargeDTO> largeList() {
		ArrayList<LargeDTO> list = new ArrayList<LargeDTO>();
		LargeDTO dto = null;
		String sql = "SELECT largeIdx,name,contentCode FROM large";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new LargeDTO();
				dto.setLargeIdx(rs.getInt("largeIdx"));
				dto.setName(rs.getString("name"));
				dto.setContentCode(rs.getString("contentCode"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<MediumDTO> mediumList() {
		ArrayList<MediumDTO> list = new ArrayList<MediumDTO>();
		MediumDTO dto = null;
		String sql = "SELECT mediumCode,name,largeIdx FROM medium ORDER BY name";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new MediumDTO();
				dto.setMediumCode(rs.getString("mediumCode"));
				dto.setName(rs.getString("name"));
				dto.setLargeIdx(rs.getString("largeIdx"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<SmallDTO> smallList() {
		ArrayList<SmallDTO> list = new ArrayList<SmallDTO>();
		SmallDTO dto = null;
		String sql = "SELECT smallCode,name,mediumCode FROM small ORDER BY name";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new SmallDTO();
				dto.setSmallCode(rs.getString("smallCode"));
				dto.setName(rs.getString("name"));
				dto.setMediumCode(rs.getString("mediumCode"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

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
		String sql = "SELECT contentId,firstImage, title, bookmarkCnt, reg_date FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY "+alignType+" DESC) AS rnum, "
				+ "contentId,areaCode,contentCode,bookmarkCnt,firstImage,title,reg_date FROM trip WHERE "+searchType+" LIKE ?"
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
				dto.setBookmarkCnt(rs.getInt("bookmarkCnt"));
				dto.setRegDate(rs.getTimestamp("reg_date"));
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
			String sql = "SELECT COUNT(contentId) FROM trip WHERE "+type+" LIKE ?";
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

	public HashMap<String, Object> tripManageList(int page) {
		HashMap<String, Object> tripMap = new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		int maxPage = 0;
		System.out.println("end : " + end + " / start : " + start);
		
		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
		TripDTO tripDTO = null;
		String sql = "SELECT contentId, title, reg_date, deactivate FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY title) AS rnum, "
				+ "contentId, title, reg_date, deactivate FROM trip"
				+ ") WHERE rnum BETWEEN ? AND ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				tripDTO = new TripDTO();
				tripDTO.setContentId(rs.getInt("contentId"));
				tripDTO.setTitle(rs.getString("title"));
				tripDTO.setReg_date(rs.getDate("reg_date"));
				tripDTO.setDeactivate(rs.getString("deactivate"));
				list.add(tripDTO);
			}
			maxPage = getTripListMaxPage(pagePerCnt);
			System.out.println("max page : " + maxPage);
			tripMap.put("tripList", list);
			tripMap.put("maxPage", maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return tripMap;
	}

	private int getTripListMaxPage(int pagePerCnt) {
		int maxPage = 0;
		try {
			String sql = "SELECT COUNT(contentId) FROM trip";
			ps = conn.prepareStatement(sql);
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

	public HashMap<String, Object> tripSearch(int page, String tripKeyword, String tripSearchType) {
		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
		TripDTO dto = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 페이징
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		int maxPage = 0;
		
		// 검색어
		String addKeyword = "%"+tripKeyword+"%";
		String sql = "SELECT contentId, title, reg_date, deactivate FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) AS rnum, "
				+ "contentId, title, reg_date, deactivate FROM trip WHERE "+tripSearchType+" LIKE ?"
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
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setDeactivate(rs.getString("deactivate"));
				list.add(dto);
			}
			maxPage = getSearchTripMaxPage(pagePerCnt, tripKeyword, tripSearchType);
			map.put("maxPage", maxPage);
			map.put("tripList", list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}
	
	private int getSearchTripMaxPage(int pagePerCnt, String keyword, String type) {
		int maxPage = 0;
		try {
			String addKeyword = "%"+keyword+"%";
			String sql = "SELECT COUNT(contentId) FROM trip WHERE "+type+" LIKE ?";
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

	public TripDTO tripManageDetail(String contentId) {
		TripDTO tripDTO = null;
		
		String sql="SELECT contentId, firstImage, t.latitude, t.longitude, t.address, t.title, t.contentCode, t.largeIdx, t.mediumCode, t.smallCode, "
				+ "t.areaCode, t.cityCode, t.managerId, t.overview, "
				+ "ct.Name AS contentName, l.name AS largeName, m.name AS mediumName, s.name AS smallName, a.name AS areaName, c.name as cityName "
				+ "FROM trip t JOIN large l ON t.largeIdx = l.largeIdx "
				+ "JOIN contenttype ct ON t.contentCode = ct.contentCode "
				+ "JOIN medium m ON t.mediumcode = m.mediumcode " 
				+ "JOIN small s ON t.smallcode = s.smallcode "
				+ "JOIN area a ON t.areacode = a.areacode "
				+ "JOIN city c ON t.citycode = c.citycode " 
				+ "AND contentId=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, contentId);
			rs = ps.executeQuery();
			if(rs.next()) {
				tripDTO = new TripDTO();
				tripDTO.setContentId(rs.getInt("contentId"));
				tripDTO.setFirstImage(rs.getString("firstImage"));
				tripDTO.setLatitude(rs.getString("latitude"));
				tripDTO.setLongitude(rs.getString("longitude"));
				tripDTO.setAddress(rs.getString("address"));
				tripDTO.setTitle(rs.getString("title"));
				tripDTO.setContentCode(rs.getString("contentCode"));
				tripDTO.setLargeIdx(rs.getString("largeIdx"));
				tripDTO.setMediumCode(rs.getString("mediumCode"));
				tripDTO.setSmallCode(rs.getString("smallCode"));
				tripDTO.setAreaCode(rs.getString("areaCode"));
				tripDTO.setCityCode(rs.getString("cityCode"));
				tripDTO.setOverview(rs.getString("overview"));
				tripDTO.setManagerId(rs.getString("managerId"));
				tripDTO.setContentName(rs.getString("contentName"));
				tripDTO.setLargeName(rs.getString("largeName"));
				tripDTO.setMediumName(rs.getString("mediumName"));
				tripDTO.setSmallName(rs.getString("smallName"));
				tripDTO.setAreaName(rs.getString("areaName"));
				tripDTO.setCityName(rs.getString("cityName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return tripDTO;
	}

	public boolean tripManageUpdate(TripDTO tripDTO) {
		boolean success = false;
		
		String sql = "UPDATE trip SET managerId = ?, firstImage = ?, latitude = ?, longitude = ?, address = ?, title = ?, largeIdx = ?, contentCode = ?, "
				+ "mediumCode = ?, smallCode = ?, areaCode = ?, cityCode = ?, overview = ? WHERE contentId = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, tripDTO.getManagerId());
			ps.setString(2, tripDTO.getFirstImage());
			ps.setString(3, tripDTO.getLatitude());
			ps.setString(4, tripDTO.getLongitude());
			ps.setString(5, tripDTO.getAddress());
			ps.setString(6, tripDTO.getTitle());
			ps.setString(7, tripDTO.getLargeIdx());
			ps.setString(8, tripDTO.getContentCode());
			ps.setString(9, tripDTO.getMediumCode());
			ps.setString(10, tripDTO.getSmallCode());
			ps.setString(11, tripDTO.getAreaCode());
			ps.setString(12, tripDTO.getCityCode());
			ps.setString(13, tripDTO.getOverview());
			ps.setInt(14, tripDTO.getContentId());
			if(ps.executeUpdate() > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return success;
	}
}
