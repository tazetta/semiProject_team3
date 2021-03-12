package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.TripDAO;
import com.mvc.dto.AreaDTO;
import com.mvc.dto.CityDTO;
import com.mvc.dto.ContentDTO;
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
		System.out.println("local[0] : " + localCode[0]);
		
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

	public void insert() {
		String contentId = req.getParameter("contentId");
		String firstImage = req.getParameter("firstImage");
		String latitude = req.getParameter("latitude");
		String longitude = req.getParameter("longitude");
		String address = req.getParameter("address");
		String title = req.getParameter("title");
		String contentCode = req.getParameter("contentCode");
		String mediumCode = req.getParameter("mediumCode");
		String smallCode = req.getParameter("smallCode");
		String areaCode = req.getParameter("areaCode");
		String cityCode = req.getParameter("cityCode");
		String largeIdx = req.getParameter("largeIdx");
		String overview = req.getParameter("overview");
		System.out.println(contentId + " / " + firstImage + " / " + latitude + " / " + longitude + " / " + address
				+ " / " + title);
		System.out.println(contentCode + " / " + mediumCode + " / " + smallCode + " / " + areaCode + " / " + cityCode
				+ " / " + largeIdx + " / " + overview);

		TripDTO dto = new TripDTO();
		dto.setContentId(Integer.parseInt(contentId));
		dto.setFirstImage(firstImage);
		dto.setLatitude(latitude);
		dto.setLongitude(longitude);
		dto.setAddress(address);
		dto.setTitle(title);
		dto.setContentCode(contentCode);
		dto.setMediumCode(mediumCode);
		dto.setSmallCode(smallCode);
		dto.setAreaCode(areaCode);
		dto.setCityCode(cityCode);
		dto.setLargeIdx(largeIdx);
		dto.setOverview(overview);

		dao.insert(dto);

	}
}
