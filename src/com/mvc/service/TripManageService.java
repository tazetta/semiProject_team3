package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.dao.TripDAO;
import com.mvc.dao.TripManageDAO;
import com.mvc.dto.AreaDTO;
import com.mvc.dto.CityDTO;
import com.mvc.dto.ContentDTO;
import com.mvc.dto.LargeDTO;
import com.mvc.dto.MediumDTO;
import com.mvc.dto.SmallDTO;
import com.mvc.dto.TripDTO;
import com.mvc.dto.TripDetailDTO;

public class TripManageService {
	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	TripManageDAO tripManageDAO = null;

	public TripManageService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		tripManageDAO = new TripManageDAO();
	}

	private boolean isManager() {
		return (String) req.getSession().getAttribute("isManager") != null;
	}

	public void tripInsert() throws IOException {
		if (isManager()) {
			String managerId = req.getParameter("managerId");
			String contentId = req.getParameter("contentId");
			String firstImage = req.getParameter("firstImage");
			String latitude = req.getParameter("latitude");
			String longitude = req.getParameter("longitude");
			String address = req.getParameter("address");
			String title = req.getParameter("title");
			String contentType = req.getParameter("contentType");
			String large = req.getParameter("large");
			String medium = req.getParameter("medium");
			String small = req.getParameter("small");
			String area = req.getParameter("area");
			String city = req.getParameter("city");
			String overview = req.getParameter("overview");
			System.out.println(managerId + " / " + contentId + " / " + firstImage + " / " + latitude + " / " + longitude
					+ " / " + address + " / " + title);
			System.out.println(contentType + " / " + medium + " / " + small + " / " + area + " / " + city + " / "
					+ large + " / " + overview);

			TripDTO dto = new TripDTO();
			dto.setManagerId(managerId);
			dto.setContentId(Integer.parseInt(contentId));
			dto.setFirstImage(firstImage);
			dto.setLatitude(latitude);
			dto.setLongitude(longitude);
			dto.setAddress(address);
			dto.setTitle(title);
			dto.setLargeIdx(large);
			dto.setContentCode(contentType);
			dto.setMediumCode(medium);
			dto.setSmallCode(small);
			dto.setAreaCode(area);
			dto.setCityCode(city);
			dto.setOverview(overview);

			HashMap<String, Object> map = new HashMap<String, Object>();
			long idx = tripManageDAO.insert(dto);
			System.out.println("insert 성공 여부 : " + idx);

			map.put("contentId", idx);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			resp.getWriter().print(json);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripInsertOverlay() throws IOException {
		if (isManager()) {
			String contentId = req.getParameter("contentId");
			System.out.println("contentId overlay : " + contentId);
			boolean success = false;

			HashMap<String, Object> map = new HashMap<String, Object>();

			success = tripManageDAO.tripInsertOverlay(contentId);
			map.put("use", success);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			resp.getWriter().print(json);
		}

	}

	public void tripInsetrInformation() throws ServletException, IOException {
		if (isManager()) {
			String tripNav = req.getParameter("tripNav");
			System.out.println("tripNav : " + tripNav);

			ArrayList<ContentDTO> contentList = null;
			ArrayList<LargeDTO> largeList = null;
			ArrayList<MediumDTO> mediumList = null;
			ArrayList<SmallDTO> smallList = null;
			ArrayList<AreaDTO> areaList = null;
			ArrayList<CityDTO> cityList = null;
			TripDAO tripDAO = new TripDAO();
			try {
				contentList = tripDAO.contentList();
				largeList = tripManageDAO.largeList();
				mediumList = tripManageDAO.mediumList();
				smallList = tripManageDAO.smallList();
				areaList = tripDAO.areaList();
				cityList = tripDAO.cityList("0");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				tripDAO.resClose();
				tripManageDAO.resClose();
				
				req.setAttribute("tripNav", tripNav);
				req.setAttribute("contentList", contentList);
				req.setAttribute("largeList", largeList);
				req.setAttribute("mediumList", mediumList);
				req.setAttribute("smallList", smallList);
				req.setAttribute("areaList", areaList);
				req.setAttribute("cityList", cityList);

				RequestDispatcher dis = req.getRequestDispatcher("tripInsert.jsp");
				dis.forward(req, resp);
			}
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripManageList() throws ServletException, IOException {
		if (isManager()) {
			String pageParam = req.getParameter("page");
			String tripNav = req.getParameter("tripNav");
			if (tripNav == null) {
				tripNav = "99";
			}
			System.out.println("tripNav : " + tripNav);
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			HashMap<String, Object> tripMap = tripManageDAO.tripManageList(group);

			req.getSession().setAttribute("type", "manageList");
			req.setAttribute("tripNav", tripNav);
			req.setAttribute("deactivate", "FALSE");
			req.setAttribute("tripList", tripMap.get("tripList"));
			req.setAttribute("maxPage", tripMap.get("maxPage"));
			req.setAttribute("currPage", group);
			RequestDispatcher dis = req.getRequestDispatcher("tripManageList.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripSearch() throws ServletException, IOException {
		if (isManager()) {
			String pageParam = req.getParameter("page");
			String searchType = req.getParameter("searchType");
			String keyword = req.getParameter("keyword");
			String isDeactivate = req.getParameter("deactivate");
			if (isDeactivate == null) {
				isDeactivate = "FALSE";
			}
			System.out.println("isDeactivate : " + isDeactivate);
			System.out.println("pageParam : " + pageParam + " / tripSearchType : " + searchType);
			System.out.println("tripKeyword : " + keyword);
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			HashMap<String, Object> tripMap = tripManageDAO.tripSearch(group, keyword, searchType, isDeactivate);
			String url = "keyword=" + keyword + "&searchType=" + searchType + "&deactivate=" + isDeactivate;

			req.getSession().setAttribute("type", "search");
			req.getSession().setAttribute("url", url);
			req.setAttribute("keyword", keyword);
			req.setAttribute("url", url);
			req.setAttribute("tripList", tripMap.get("tripList"));
			req.setAttribute("maxPage", tripMap.get("maxPage"));
			req.setAttribute("currPage", group);
			RequestDispatcher dis = req.getRequestDispatcher("tripManageList.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripManageDetail() throws ServletException, IOException {
		if (isManager()) {
			String contentId = req.getParameter("contentId");
			String page = req.getParameter("page");
			System.out.println("contentId : " + contentId);

			TripDetailDTO tripDTO = tripManageDAO.tripManageDetail(contentId);

			req.setAttribute("currPage", page);
			req.setAttribute("tripDTO", tripDTO);
			RequestDispatcher dis = req.getRequestDispatcher("tripManageDetail.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripManageUpdateForm() throws ServletException, IOException {
		if (isManager()) {
			String contentId = req.getParameter("contentId");
			String page = req.getParameter("page");
			System.out.println("currPage : " + page);
			System.out.println("contentId : " + contentId);

			ArrayList<ContentDTO> contentList = null;
			ArrayList<LargeDTO> largeList = null;
			ArrayList<MediumDTO> mediumList = null;
			ArrayList<SmallDTO> smallList = null;
			ArrayList<AreaDTO> areaList = null;
			ArrayList<CityDTO> cityList = null;
			TripDAO tripDAO = new TripDAO();
			TripManageDAO tripManageDAO = new TripManageDAO();
			try {
				contentList = tripDAO.contentList();
				largeList = tripManageDAO.largeList();
				mediumList = tripManageDAO.mediumList();
				smallList = tripManageDAO.smallList();
				areaList = tripDAO.areaList();
				cityList = tripDAO.cityList("0");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				tripDAO.resClose();
				tripManageDAO.resClose();
				
				tripManageDAO = new TripManageDAO();
				TripDetailDTO tripDTO = tripManageDAO.tripManageDetail(contentId);

				req.setAttribute("currPage", page);

				req.setAttribute("contentList", contentList);
				req.setAttribute("largeList", largeList);
				req.setAttribute("mediumList", mediumList);
				req.setAttribute("smallList", smallList);
				req.setAttribute("areaList", areaList);
				req.setAttribute("cityList", cityList);
				req.setAttribute("tripDTO", tripDTO);
				RequestDispatcher dis = req.getRequestDispatcher("tripManageUpdateForm.jsp");
				dis.forward(req, resp);
			}
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripManageUpdate() throws ServletException, IOException {
		if (isManager()) {
			String managerId = req.getParameter("managerId");
			String contentId = req.getParameter("contentId");
			String firstImage = req.getParameter("firstImage");
			String latitude = req.getParameter("latitude");
			String longitude = req.getParameter("longitude");
			String address = req.getParameter("address");
			String title = req.getParameter("title");
			String contentType = req.getParameter("contentType");
			String large = req.getParameter("large");
			String medium = req.getParameter("medium");
			String small = req.getParameter("small");
			String area = req.getParameter("area");
			String city = req.getParameter("city");
			String overview = req.getParameter("overview");
			String deactivate = req.getParameter("deactivate");
			String currPage = req.getParameter("page");
			System.out.println("currPage : " + currPage);
			System.out.println(managerId + " / " + contentId + " / " + firstImage + " / " + latitude + " / " + longitude
					+ " / " + address + " / " + title);
			System.out.println(contentType + " / " + medium + " / " + small + " / " + area + " / " + city + " / "
					+ large + " / " + overview + " / " + deactivate);
			TripDTO tripDTO = new TripDTO();
			tripDTO.setManagerId(managerId);
			tripDTO.setContentId(Integer.parseInt(contentId));
			tripDTO.setFirstImage(firstImage);
			tripDTO.setLatitude(latitude);
			tripDTO.setLongitude(longitude);
			tripDTO.setAddress(address);
			tripDTO.setTitle(title);
			tripDTO.setLargeIdx(large);
			tripDTO.setContentCode(contentType);
			tripDTO.setMediumCode(medium);
			tripDTO.setSmallCode(small);
			tripDTO.setAreaCode(area);
			tripDTO.setCityCode(city);
			tripDTO.setOverview(overview);
			tripDTO.setDeactivate(deactivate);
			boolean success = tripManageDAO.tripManageUpdate(tripDTO);
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("success", success);
			map.put("currPage", currPage);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			resp.getWriter().print(json);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripDeactivateFilter() throws ServletException, IOException {
		if (isManager()) {
			String pageParam = req.getParameter("page");
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			HashMap<String, Object> tripMap = tripManageDAO.tripDeactivateFilter(group);
			String url = "deactivate=TRUE";
			req.getSession().setAttribute("type", "filter");
			req.getSession().setAttribute("url", url);
			req.setAttribute("url", url);
			req.setAttribute("deactivate", "TRUE");
			req.setAttribute("tripList", tripMap.get("tripList"));
			req.setAttribute("maxPage", tripMap.get("maxPage"));
			req.setAttribute("currPage", group);
			RequestDispatcher dis = req.getRequestDispatcher("tripManageList.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}
}
