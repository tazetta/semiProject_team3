package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.ManagerService;

@WebServlet({"/managerList","/managerDel","/tripInsert","/tripInsertOverlay","/tripInsertInformation",})
public class ManagerController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String sub = uri.substring(ctx.length());
		
		ManagerService service = new ManagerService(req,resp);
		
		switch(sub) {
		case  "/managerList":
			System.out.println("관리자 목록 요청");
			service.managerList();
			break;
			
		case  "/managerDel":
			System.out.println("관리자 목록 요청");
			service.managerDel();
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
		}
	}

}
