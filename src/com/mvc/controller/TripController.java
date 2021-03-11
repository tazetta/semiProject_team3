package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.TripService;

@WebServlet({"/themeContentList","/areaContentList","/tripInsert","/themeResult","/areaContentResult"})
public class TripController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String subAddr = req.getRequestURI().substring(req.getContextPath().length());
		
		TripService service = new TripService(req,resp);
		System.out.println("get 요청");
		switch(subAddr) {
			case "/themeContentList":
				System.out.println("contentList 요청");
				service.contentList();
				break;
			
			case "/themeResult":
				System.out.println("페이지 이동 요청");
				service.themeResult();
				break;
				
			case "/areaContentList":
				System.out.println("areaContentList 요청");
				service.areaContentList();
				break;
				
			case "/areaContentResult":
				System.out.println("areaContentResult 요청");
				service.areaContentResult();
				break;
				
			case "/tripInsert":
				System.out.println("데이터 삽입");
				service.insert();
				break;
				
		}
	}

	
}
