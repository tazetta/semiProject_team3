package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.ManagerService;
import com.mvc.service.MemberListService;

@WebServlet({ "/memberList","/memberDetail" })
public class MemberListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String sub = uri.substring(ctx.length());

		MemberListService service = new MemberListService(req, resp);

		switch (sub) {
		case "/memberList":
			System.out.println("일반 회원 목록 요청");
			service.memberList();
		break;
		
		case "/memberDetail":
			System.out.println("일반 회원 상세보기 요청");
			service.memberDetail();
			break;
		}
	}
}
