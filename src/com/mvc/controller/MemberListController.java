package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.MemberListService;

@WebServlet({ "/memberList", "/memberDetail", "/memberSearch", "/memberDelList", "/memberDelDetail", "/memberDraw",
		"/memberRestore", "/memberBlackAddForm", "/memberBlackList", "/memberBlackAdd", "/memberBlackDetail" })
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

		case "/memberSearch":
			System.out.println("회원 검색 결과 요청");
			service.memberSearch();
			break;

		case "/memberDelList":
			System.out.println("탈퇴 회원 목록 요청");
			service.memberDelList();
			break;

		case "/memberDelDetail":
			System.out.println("탈퇴 회원 상세보기 요청");
			service.memberDelDetail();
			break;

		case "/memberDraw":
			System.out.println("탈퇴 회원 삭제 요청");
			service.memberDraw();
			break;

		case "/memberRestore":
			System.out.println("탈퇴 회원 복구 요청");
			service.memberRestore();
			break;

		case "/memberBlackAddForm":
			System.out.println("블랙리스트 사유 추가 요청");
			service.memberBlackAddForm();
			break;

		case "/memberBlackList":
			System.out.println("블랙리스트 회원 목록 요청");
			service.memberBlackList();
			break;

		case "/memberBlackAdd":
			System.out.println("블랙리스트 추가 요청");
			service.memberBlackAdd();
			break;

		case "/memberBlackDetail":
			System.out.println("블랙리스트 회원 상세보기");
			service.memberBlackDetail();
			break;
		}
	}
}