package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.BlackListService;

@WebServlet({"/memberBlackList","/memberBlackAdd"})
public class BlackListController extends HttpServlet {

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

		BlackListService service = new BlackListService(req,resp);
		
		switch (sub) {
		case "/memberBlackList":
			System.out.println("블랙리스트 회원 목록 요청");
			service.memberBlackList();
		break;
		
		case "/memberBlackAdd":
			System.out.println("블랙리스트 추가 요청");
			service.memberBlackAdd();
			break;
		}
	}
}
