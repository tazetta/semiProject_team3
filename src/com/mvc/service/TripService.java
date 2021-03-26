package com.mvc.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.dao.TripDAO;
import com.mvc.dto.AreaDTO;
import com.mvc.dto.CityDTO;
import com.mvc.dto.ContentDTO;
import com.mvc.dto.LargeDTO;
import com.mvc.dto.MediumDTO;
import com.mvc.dto.SmallDTO;
import com.mvc.dto.TripDTO;
import com.mvc.dto.TripDetailDTO;

public class TripService {
	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	TripDAO dao = null;

	public TripService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		dao = new TripDAO();
	}

	public void contentList() throws ServletException, IOException {
		String nav = req.getParameter("nav");
		if (nav == null) { // 지역별 메뉴를 눌렀을 때 관광지를 제일 먼저 보여준다.
			nav = "12";
		}
		ArrayList<ContentDTO> contentList = null;
		ArrayList<AreaDTO> areaList = null;
		try {
			contentList = dao.contentList();
			areaList = dao.areaList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();

			req.setAttribute("contentList", contentList);
			req.setAttribute("areaList", areaList);
			req.setAttribute("nav", nav);

			RequestDispatcher dis = req.getRequestDispatcher("contentList.jsp");
			dis.forward(req, resp);
		}
	}

	public void resultList() throws ServletException, IOException {
		StringBuilder url = new StringBuilder();
		String pageParam = req.getParameter("page");
		String nav = req.getParameter("nav");
		String[] localCode = req.getParameterValues("local");
		String type = req.getParameter("type");
		System.out.println("type : " + type + " / page : " + pageParam + " / nav : " + nav);

		ArrayList<ContentDTO> contentList = null;
		ArrayList<AreaDTO> areaList = null;
		ArrayList<CityDTO> cityList = null;

		try {
			contentList = dao.contentList();
			areaList = dao.areaList();
			if (type.equals("area")) {
				cityList = dao.cityList(nav);
				req.setAttribute("cityList", cityList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();

			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			url.append("nav=" + nav);
			url.append("&type=" + type);
			for (int i = 0; i < localCode.length; i++) {
				url.append("&local=" + localCode[i]);
			}

			dao = new TripDAO();
			HashMap<String, Object> map = dao.resultList(group, nav, localCode, type);
			System.out.println("map.get(maxpage) : " + map.get("maxPage"));

			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("list", map.get("list"));
			req.setAttribute("currPage", group);
			req.setAttribute("url", url);
			req.setAttribute("nav", nav);
			req.setAttribute("contentList", contentList);
			req.setAttribute("areaList", areaList);

			String page = "contentResult.jsp";
			if (type.equals("area")) {
				page = "areaContentResult.jsp";
			}

			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}
	}

	public void areaContentList() throws ServletException, IOException {
		String nav = req.getParameter("nav");
		System.out.println("areaCode : " + nav);
		if (nav == null) { // 지역별 메뉴를 눌렀을 때 서울을 먼저 보여준다.
			nav = "1";
		}

		ArrayList<AreaDTO> areaList = null;
		ArrayList<CityDTO> cityList = null;
		try {
			areaList = dao.areaList();
			cityList = dao.cityList(nav);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();

			req.setAttribute("areaList", areaList);
			req.setAttribute("cityList", cityList);
			req.setAttribute("nav", nav);

			RequestDispatcher dis = req.getRequestDispatcher("areaContentList.jsp");
			dis.forward(req, resp);
		}
	}

	public void search() throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		String searchType = req.getParameter("searchType");
		String alignType = req.getParameter("alignType");
		String pageParam = req.getParameter("page");
		if (alignType == null) {
			alignType = "bookmarkCnt";
		}
		System.out.println("keyword : " + keyword + " / searchType : " + searchType + " / page : " + pageParam
				+ " / alignType : " + alignType);

		int group = 1;
		if (pageParam != null) {
			group = Integer.parseInt(pageParam);
		}

		String url = "keyword=" + keyword + "&searchType=" + searchType + "&alignType=" + alignType;
		HashMap<String, Object> map = dao.search(group, keyword, searchType, alignType);
		System.out.println("map.get(maxpage) : " + map.get("maxPage"));

		req.setAttribute("keyword", keyword);
		req.setAttribute("searchType", searchType);
		req.setAttribute("url", url);
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("list", map.get("list"));
		req.setAttribute("currPage", group);

		RequestDispatcher dis = req.getRequestDispatcher("searchResult.jsp");
		dis.forward(req, resp);
	}

	/* 인기 여행지 사진 */
	public void popularImage() throws IOException {
		String img = req.getParameter("img");
		System.out.println("img:" + img);
		ArrayList<TripDTO> list = dao.popularImage();
		HashMap<String, Object> map = new HashMap<String, Object>();

		boolean success = false;
		if (list != null) {
			success = true;
			System.out.println("list.size:" + list.size());
		}
		map.put("list", list);
		map.put("success", success);
		dao.resClose();
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println("json:" + json);

		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		PrintWriter out = resp.getWriter();
		out.print(json);

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
			long idx = dao.insert(dto);
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

			success = dao.tripInsertOverlay(contentId);
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
			try {
				contentList = dao.contentList();
				largeList = dao.largeList();
				mediumList = dao.mediumList();
				smallList = dao.smallList();
				areaList = dao.areaList();
				cityList = dao.cityList(null);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dao.resClose();

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

			HashMap<String, Object> tripMap = dao.tripManageList(group);

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

			HashMap<String, Object> tripMap = dao.tripSearch(group, keyword, searchType, isDeactivate);
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

			TripDetailDTO tripDTO = dao.tripManageDetail(contentId);

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
			try {
				contentList = dao.contentList();
				largeList = dao.largeList();
				mediumList = dao.mediumList();
				smallList = dao.smallList();
				areaList = dao.areaList();
				cityList = dao.cityList(null);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dao.resClose();

				dao = new TripDAO();
				TripDetailDTO tripDTO = dao.tripManageDetail(contentId);

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
			boolean success = dao.tripManageUpdate(tripDTO);

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
			HashMap<String, Object> tripMap = dao.tripDeactivateFilter(group);
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
