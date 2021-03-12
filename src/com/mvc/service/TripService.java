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
import com.mvc.dto.AreaDTO;
import com.mvc.dto.CityDTO;
import com.mvc.dto.ContentDTO;
import com.mvc.dto.LargeDTO;
import com.mvc.dto.MediumDTO;
import com.mvc.dto.SmallDTO;
import com.mvc.dto.TripDTO;

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
		ArrayList<ContentDTO> list = dao.contentList();
		ArrayList<AreaDTO> areaList = dao.areaList();
		dao.resClose();

		req.setAttribute("contentList", list);
		req.setAttribute("areaList", areaList);
		req.setAttribute("nav", nav);
		RequestDispatcher dis = req.getRequestDispatcher("contentList.jsp");
		dis.forward(req, resp);
	}

	public void resultList() throws ServletException, IOException {
		StringBuffer url = new StringBuffer();
		String pageParam = req.getParameter("page");
		String nav = req.getParameter("nav");
		String[] localCode = req.getParameterValues("local");
		String type = req.getParameter("type");
		System.out.println("type : " + type);
		System.out.println("page : " + pageParam);
		System.out.println("nav : " + nav);

		if (localCode != null) {

			ArrayList<ContentDTO> contentList = dao.contentList();
			ArrayList<AreaDTO> areaList = dao.areaList();
			if (type.equals("area")) {
				ArrayList<CityDTO> cityList = dao.cityList(nav);
				req.setAttribute("cityList", cityList);
			}
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			url.append("nav=" + nav);
			url.append("&type=" + type);
			for (int i = 0; i < localCode.length; i++) {
				url.append("&local=" + localCode[i]);
			}

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
		} else {
			if (type.equals("theme")) {
				resp.sendRedirect("themeContentList");
			} else if (type.equals("area")) {
				resp.sendRedirect("areaContentList");
			}
		}
	}

	public void areaContentList() throws ServletException, IOException {
		String nav = req.getParameter("nav");
		System.out.println("areaCode : " + nav);
		if (nav == null) {
			nav = "1";
		}
		ArrayList<AreaDTO> areaList = dao.areaList();
		ArrayList<CityDTO> cityList = dao.cityList(nav);
		dao.resClose();

		req.setAttribute("areaList", areaList);
		req.setAttribute("cityList", cityList);
		req.setAttribute("nav", nav);
		RequestDispatcher dis = req.getRequestDispatcher("areaContentList.jsp");
		dis.forward(req, resp);
	}

	public void tripInsert() throws IOException {

		if (isManagered()) {
			boolean success = false;
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
			try {
				success = dao.insert(dto);
				System.out.println("insert 성공 여부 : " + success);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
				map.put("success", success);
				Gson gson = new Gson();
				String json = gson.toJson(map);
				resp.getWriter().print(json);
			}
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripInsertOverlay() throws IOException {
		String contentId = req.getParameter("contentId");
		System.out.println("contentId overlay : " + contentId);
		boolean success = false;

		TripDAO dao = new TripDAO();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			success = dao.tripInsertOverlay(contentId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
			map.put("use", success);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			resp.getWriter().print(json);
		}

	}

	public void tripInsetrInformation() throws ServletException, IOException {

		if (isManagered()) {
			dao = new TripDAO();
			ArrayList<ContentDTO> contentList = dao.contentList();
			ArrayList<LargeDTO> largeList = dao.largeList();
			ArrayList<MediumDTO> mediumList = dao.mediumList();
			ArrayList<SmallDTO> smallList = dao.smallList();
			ArrayList<AreaDTO> areaList = dao.areaList();
			ArrayList<CityDTO> cityList = dao.cityList("0");
			dao.resClose();

			req.setAttribute("contentList", contentList);
			req.setAttribute("largeList", largeList);
			req.setAttribute("mediumList", mediumList);
			req.setAttribute("smallList", smallList);
			req.setAttribute("areaList", areaList);
			req.setAttribute("cityList", cityList);

			RequestDispatcher dis = req.getRequestDispatcher("tripInsert.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	private boolean isManagered() {
		String loginId = (String) req.getSession().getAttribute("loginId");
		return dao.chkManager(loginId);
	}
}
