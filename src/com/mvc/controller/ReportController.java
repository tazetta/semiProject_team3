package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.ReportService;

@WebServlet({"/reportBBS","/repDetail","/updateYN","/reportComment","/repDetailCom"})
public class ReportController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		human(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		human(req, resp);
	}

	private void human(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		
		ReportService service = new ReportService(req,resp);
		
		switch (req.getRequestURI().substring(req.getContextPath().length())) {

		case "/reportBBS":
			System.out.println("신고게시물 리스트");
			service.reportBBS();
			break;

		case "/repDetail":
			System.out.println("신고게시물 상세보기");
			service.repDetail();
			break;			

		case "/updateYN":
			System.out.println("신고 처리 요청");
			service.updateYN();
			break;			

		case "/reportComment":
			System.out.println("신고댓글 리스트");
			service.reportComment();
			break;		

		case "/repDetailCom":
			System.out.println("신고댓글 상세보기");
			service.repDetailCom();
			break;
		}
		
	}
}
