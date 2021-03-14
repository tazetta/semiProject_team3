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

	public ArrayList<CityDTO> list(String areaCode) {
		ArrayList<CityDTO> list = new ArrayList<CityDTO>();
		CityDTO dto = null;

		String sql = "SELECT cityCode,name,areaCode FROM city WHERE areaCode = ? ORDER BY cityCode";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, areaCode);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CityDTO();
				dto.setCityCode(rs.getInt("cityCode"));
				dto.setName(rs.getString("name"));
				dto.setAreaCode(rs.getString("areaCode"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}

		return list;
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
			} else if (areaCode.equals("0")) {
				String sql = "SELECT c.cityCode, c.name, a.name FROM city c, area a WHERE c.areacode = a.areacode ORDER BY citycode";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					dto = new CityDTO();
					dto.setCityCode(rs.getInt(1));
					dto.setName(rs.getString(2));
					dto.setAreaName(rs.getString(3));
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, Object> resultList(int page, String nav, String[] localCode, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		int maxPage = 0;
		System.out.println("end : " + end + " / start : " + start);

		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
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
		System.out.println("inSQL : " + inSQL);
		System.out.println("insertSQL : " + insertSQL);
		System.out.println("SQL : " + sql);
		try {
			ps = conn.prepareStatement(sql);

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
			ps.setString(localCode.length+1, nav);
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
		String sql = "SELECT mediumCode,name FROM medium";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new MediumDTO();
				dto.setMediumCode(rs.getString("mediumCode"));
				dto.setName(rs.getString("name"));
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
		String sql = "SELECT smallCode,name FROM small";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new SmallDTO();
				dto.setSmallCode(rs.getString("smallCode"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean chkManager(String loginId) {
		boolean success = false;
		String sql = "SELECT managerId FROM manager WHERE managerId=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			success = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}
}
