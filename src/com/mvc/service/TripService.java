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
		StringBuffer url = new StringBuffer();
		String pageParam = req.getParameter("page");
		String nav = req.getParameter("nav");
		String[] localCode = req.getParameterValues("local");
		String type = req.getParameter("type");
		System.out.println("type : " + type);
		System.out.println("page : " + pageParam);
		System.out.println("nav : " + nav);

		ArrayList<ContentDTO> contentList = null;
		ArrayList<AreaDTO> areaList = null;
		ArrayList<CityDTO> cityList = null;
		if (localCode != null) {
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
		System.out.println("keyword : " + keyword);
		System.out.println("searchType : " + searchType);
		System.out.println("page : " + pageParam);

		if (alignType == null) {
			alignType = "bookmarkCnt";
		}
		System.out.println("alignType : " + alignType);

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
}
