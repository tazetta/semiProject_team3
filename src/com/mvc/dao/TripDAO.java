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
		String sql = "SELECT cityCode,name FROM city WHERE areacode=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, areaCode);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new CityDTO();
				dto.setCityCode(rs.getInt("cityCode"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public HashMap<String, Object> themeResult(int page, String contentCode, String[] areaCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		int pagePerCnt = 10/areaCode.length;
		System.out.println("areaCode.length : " + areaCode);
		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);
		int maxPage = 0;
		System.out.println("end : " + end + " / start : " + start);

		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
		try {
			for (int i = 0; i < areaCode.length; i++) {
				String sql = "SELECT contentId,areaCode,contentCode,firstImage,bookmarkCnt, title, reg_date FROM ("
						+ "SELECT ROW_NUMBER() OVER(ORDER BY reg_date DESC) AS rnum, "
						+ "contentId,areaCode,contentCode,bookmarkCnt,firstImage,title,reg_date FROM trip WHERE areacode=? and contentcode=?"
						+ ") WHERE rnum BETWEEN ? AND ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, areaCode[i]);
				ps.setString(2, contentCode);
				ps.setInt(3, start);
				ps.setInt(4, end);
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
			}
			maxPage = getMaxPage(contentCode, areaCode, pagePerCnt);
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

	private int getMaxPage(String contentCode, String[] areaCode, int pagePerCnt) {
		int maxPage = 0;
		try {
			for (int i = 0; i < areaCode.length; i++) {
				String sql = "SELECT COUNT(contentId) FROM trip WHERE contentCode=? AND areaCode=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, contentCode);
				ps.setString(2, areaCode[i]);
				rs = ps.executeQuery();
				if(rs.next()) {
					int cnt = rs.getInt(1);
					maxPage += (int) Math.ceil(cnt/(double)pagePerCnt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxPage;
	}
	
	public HashMap<String, Object> areaContentResult(int group, String areaCode, String[] cityCode) {
		return null;
	}

	public void insert(TripDTO dto) {
		String sql = "INSERT INTO trip(contentId,firstImage,latitude,longitude,address,title,"
				+ "contentCode,mediumCode,smallCode,areaCode,cityCode,largeIdx,overview) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int success = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dto.getContentId());
			ps.setString(2, dto.getFirstImage());
			ps.setString(3, dto.getLatitude());
			ps.setString(4, dto.getLongitude());
			ps.setString(5, dto.getAddress());
			ps.setString(6, dto.getTitle());
			ps.setString(7, dto.getContentCode());
			ps.setString(8, dto.getMediumCode());
			ps.setString(9, dto.getSmallCode());
			ps.setString(10, dto.getAreaCode());
			ps.setString(11, dto.getCityCode());
			ps.setString(12, dto.getLargeIdx());
			ps.setString(13, dto.getOverview());
			success = ps.executeUpdate();
			System.out.println("insert 성공한 여부 : " + success);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}
