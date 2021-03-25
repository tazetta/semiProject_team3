package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.TripService;

@WebServlet({ "/themeContentList", "/areaContentList", "/resultList", "/search","/popularImage", 
					"/tripInsert", "/tripInsertOverlay", "/tripInsertInformation", "/tripManageList", "/tripSearch",
					"/tripManageDetail", "/tripManageUpdateForm", "/tripManageUpdate", "/tripDeactivateFilter" })
public class TripController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String subAddr = req.getRequestURI().substring(req.getContextPath().length());

		TripService service = new TripService(req, resp);

		switch (subAddr) {
			case "/themeContentList":
				System.out.println("contentList 요청");
				service.contentList();
				break;
	
			case "/areaContentList":
				System.out.println("areaContentList 요청");
				service.areaContentList();
				break;
	
			case "/resultList":
				System.out.println("resultList 요청");
				service.resultList();
				break;
	
			case "/search":
				System.out.println("search 요청");
				service.search();
				break;
				
			case "/popularImage":
				System.out.println("");
				System.out.println("--인기있는 여행지 사진--");
				service.popularImage();
				break;
				
			case "/tripInsertInformation":
				System.out.println("tripInsertInformation 요청");
				service.tripInsetrInformation();
				break;
	
			case "/tripInsertOverlay":
				System.out.println("tripInsertOverlay 요청");
				service.tripInsertOverlay();
				break;
	
			case "/tripInsert":
				System.out.println("tripInsert 요청");
				service.tripInsert();
				break;
	
			case "/tripManageList":
				System.out.println("tripManageList 요청");
				service.tripManageList();
				break;
	
			case "/tripSearch":
				System.out.println("tripSearch 요청");
				service.tripSearch();
				break;
	
			case "/tripManageDetail":
				System.out.println("tripManageDetail 요청");
				service.tripManageDetail();
				break;
	
			case "/tripManageUpdateForm":
				System.out.println("tripManageUpdateForm 요청");
				service.tripManageUpdateForm();
				break;
	
			case "/tripManageUpdate":
				System.out.println("tripManageUpdate 요청");
				service.tripManageUpdate();
				break;
	
			case "/tripDeactivateFilter":
				System.out.println("tripDeactivateFilter 요청");
				service.tripDeactivateFilter();
				break;
		}
	}

}
