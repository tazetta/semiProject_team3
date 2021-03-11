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
		ArrayList<ContentDTO> list = dao.contentList();
		ArrayList<AreaDTO> areaList = dao.areaList();
		dao.resClose();

		req.setAttribute("contentList", list);
		req.setAttribute("areaList", areaList);
		RequestDispatcher dis = req.getRequestDispatcher("contentList.jsp");
		dis.forward(req, resp);
	}

	public void themeResult() throws ServletException, IOException {
		StringBuffer url = new StringBuffer();
		String pageParam = req.getParameter("page");
		String contentCode = req.getParameter("content");
		String[] areaCode = req.getParameterValues("city");
		System.out.println("page : " + pageParam);
		System.out.println(contentCode + " / " + areaCode[0]);
		ArrayList<ContentDTO> contentList = dao.contentList();
		ArrayList<AreaDTO> areaList = dao.areaList();
//		ArrayList<TripDTO> list = dao.contentSearch(contentCode, areaCode);
		
		int group = 1; 
		if(pageParam != null) {
			group = Integer.parseInt(pageParam);
		}
		url.append("content="+contentCode);
		for(int i = 0; i < areaCode.length;i++) {
			url.append("&city="+areaCode[i]);
		}
		System.out.println(url);
		HashMap<String, Object> map = dao.themeResult(group, contentCode, areaCode);
		System.out.println("map.get(maxpage) : " + map.get("maxPage"));
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("list", map.get("list"));
		req.setAttribute("currPage", group);
		
		req.setAttribute("url", url);
		req.setAttribute("contentList", contentList);
		req.setAttribute("areaList", areaList);
		RequestDispatcher dis = req.getRequestDispatcher("contentResult.jsp");
		dis.forward(req, resp);
	}
	
	public void areaContentList() throws ServletException, IOException {
		
		String areaCode = req.getParameter("areaCode");
		System.out.println(areaCode);
		if(areaCode == null) {
			areaCode = "1";
		}
		ArrayList<AreaDTO> areaList = dao.areaList();
		ArrayList<CityDTO> cityList = dao.cityList(areaCode);
		dao.resClose();
		
		req.setAttribute("areaList", areaList);
		req.setAttribute("cityList", cityList);
		req.setAttribute("areaCode", areaCode);
		RequestDispatcher dis = req.getRequestDispatcher("areaContentList.jsp");
		dis.forward(req, resp);
	}
	
	public void areaContentResult() throws ServletException, IOException {
		StringBuffer url = new StringBuffer();
		String pageParam = req.getParameter("page");
		String areaCode = req.getParameter("areaCode");
		String[] cityCode = req.getParameterValues("city");
		System.out.println("page : " + pageParam);
		System.out.println("cityCode : " + cityCode[0] + " / areaCode : " + areaCode);
		
		ArrayList<AreaDTO> areaList = dao.areaList();
		ArrayList<CityDTO> cityList = dao.cityList(areaCode);
		
		int group = 1; 
		if(pageParam != null) {
			group = Integer.parseInt(pageParam);
		}
		url.append("content="+areaCode);
		for(int i = 0; i < cityCode.length;i++) {
			url.append("&city="+cityCode[i]);
		}
		System.out.println(url);
		HashMap<String, Object> map = dao.areaContentResult(group, areaCode, cityCode);
		System.out.println("map.get(maxpage) : " + map.get("maxPage"));
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("list", map.get("list"));
		req.setAttribute("currPage", group);
		
		req.setAttribute("url", url);
		req.setAttribute("cityList", cityList);
		req.setAttribute("areaList", areaList);
		RequestDispatcher dis = req.getRequestDispatcher("areaContentResult.jsp");
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
		System.out.println(contentId+ " / " + firstImage+ " / " + latitude+ " / " + longitude+ " / " + address+ " / " + title);
		System.out.println(contentCode+ " / " + mediumCode+ " / " + smallCode+ " / " + areaCode+ " / " + cityCode + " / " + largeIdx+ " / " + overview);
		
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
