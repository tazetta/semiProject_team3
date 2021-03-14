package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.dao.ManagerDAO;
import com.mvc.dao.TripDAO;
import com.mvc.dto.AreaDTO;
import com.mvc.dto.CityDTO;
import com.mvc.dto.ContentDTO;
import com.mvc.dto.LargeDTO;
import com.mvc.dto.ManagerDTO;
import com.mvc.dto.MediumDTO;
import com.mvc.dto.SmallDTO;
import com.mvc.dto.TripDTO;

public class ManagerService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;
	String page = "";
	String msg = "";

	public ManagerService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void managerList() throws ServletException, IOException {
		ManagerDAO dao = new ManagerDAO();
		ArrayList<ManagerDTO> managerList = dao.managerList();
		req.setAttribute("managerList", managerList);
		dis = req.getRequestDispatcher("manager_list.jsp");
		dis.forward(req, resp);
	}

	public void managerDel() throws ServletException, IOException {
		String managerid = req.getParameter("managerid");
		System.out.println("삭제할 관리자번호 : " + managerid);

		msg = "";
		page = "/adminList";

		ManagerDAO dao = new ManagerDAO();
		if (dao.adminDel(managerid)) {
			msg = "팝업을 삭제하였습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void tripInsert() throws IOException {
		if (isManager()) {
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
			TripDAO tripDAO = new TripDAO();
			success = tripDAO.insert(dto);
			System.out.println("insert 성공 여부 : " + success);

			map.put("success", success);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			resp.getWriter().print(json);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void tripInsertOverlay() throws IOException {
		// 관리자 MVC로 옮겨야 한다.
		String contentId = req.getParameter("contentId");
		System.out.println("contentId overlay : " + contentId);
		boolean success = false;

		TripDAO dao = new TripDAO();
		HashMap<String, Object> map = new HashMap<String, Object>();

		success = dao.tripInsertOverlay(contentId);
		map.put("use", success);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		resp.getWriter().print(json);

	}

	public void tripInsetrInformation() throws ServletException, IOException {
		if (isManager()) {
			ArrayList<ContentDTO> contentList = null;
			ArrayList<LargeDTO> largeList = null;
			ArrayList<MediumDTO> mediumList = null;
			ArrayList<SmallDTO> smallList = null;
			ArrayList<AreaDTO> areaList = null;
			ArrayList<CityDTO> cityList = null;
			TripDAO tripDAO = new TripDAO();
			try {
				contentList = tripDAO.contentList();
				largeList = tripDAO.largeList();
				mediumList = tripDAO.mediumList();
				smallList = tripDAO.smallList();
				areaList = tripDAO.areaList();
				cityList = tripDAO.cityList("0");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				tripDAO.resClose();

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

	private boolean isManager() {
		return (String) req.getSession().getAttribute("isManager") != null;
	}

	public void tripManage() throws ServletException, IOException {
		if (isManager()) {
			String pageParam = req.getParameter("page");
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			TripDAO dao = new TripDAO();
			HashMap<String, Object> tripMap = dao.tripManage(group);

			req.setAttribute("tripList", tripMap.get("tripList"));
			req.setAttribute("maxPage", tripMap.get("maxPage"));
			req.setAttribute("currPage", group);
			RequestDispatcher dis = req.getRequestDispatcher("tripManage.jsp");
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
			System.out.println("pageParam : " + pageParam + " / tripSearchType : " + searchType);
			System.out.println("tripKeyword : " + keyword);
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			TripDAO dao = new TripDAO();
			HashMap<String, Object> tripMap = dao.tripSearch(group, keyword, searchType);
			String url = "keyword=" + keyword + "&searchType=" + searchType;

			req.setAttribute("keyword", keyword);
			req.setAttribute("url", url);
			req.setAttribute("tripList", tripMap.get("tripList"));
			req.setAttribute("maxPage", tripMap.get("maxPage"));
			req.setAttribute("currPage", group);
			RequestDispatcher dis = req.getRequestDispatcher("tripManage.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}
}