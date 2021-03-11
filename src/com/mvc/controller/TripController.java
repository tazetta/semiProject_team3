package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.TripService;

@WebServlet({"/themeContentList","/areaList","/content","/tripInsert"})
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
			
			case "/areaList":
				System.out.println("areaList 요청");
				service.areaList();
				break;
				
			case "/content":
				System.out.println("테마별");
				service.content();
				break;
				
			case "/tripInsert":
				System.out.println("데이터 삽입");
				service.insert();
				break;
				
			
		}
	}

	
}
