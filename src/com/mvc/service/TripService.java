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
		ArrayList<ContentDTO> list = dao.content();
		ArrayList<AreaDTO> areaList = dao.area();
		dao.resClose();

		req.setAttribute("contentList", list);
		req.setAttribute("areaList", areaList);
		RequestDispatcher dis = req.getRequestDispatcher("contentList.jsp");
		dis.forward(req, resp);
	}

	public void areaList() {
	}

//	public void localList() {
//		String[] checkList = req.getParameterValues("city");
//		
//		for (String list : checkList) {
//			System.out.println("list : " + list);
//		}
//	}

	public void content() throws ServletException, IOException {
		ArrayList<ContentDTO> list = dao.content();
		ArrayList<AreaDTO> areaList = dao.area();
		dao.resClose();

		req.setAttribute("list", list);
		req.setAttribute("areaList", areaList);
		RequestDispatcher dis = req.getRequestDispatcher("contentList.jsp");
		dis.forward(req, resp);
	}

	public void list() throws ServletException, IOException {
		String areaCode = req.getParameter("areaCode");
		System.out.println("areaCode : " + areaCode);

		ArrayList<CityDTO> list = dao.list(areaCode);

		req.setAttribute("list", list);
		RequestDispatcher dis = req.getRequestDispatcher("areaList.jsp");
		dis.forward(req, resp);

	}
	public void contentResult() throws ServletException, IOException {
		StringBuffer url = new StringBuffer();
		String pageParam = req.getParameter("page");
		String contentCode = req.getParameter("content");
		String[] areaCode = req.getParameterValues("city");
		System.out.println("page : " + pageParam);
		System.out.println(contentCode + " / " + areaCode[0]);
		ArrayList<ContentDTO> contentList = dao.content();
		ArrayList<AreaDTO> areaList = dao.area();
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
		HashMap<String, Object> map = dao.pageList(group, contentCode, areaCode);
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("list", map.get("list"));
		req.setAttribute("currPage", group);
		
		req.setAttribute("url", url);
		req.setAttribute("contentList", contentList);
		req.setAttribute("areaList", areaList);
		RequestDispatcher dis = req.getRequestDispatcher("contentResult.jsp");
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
