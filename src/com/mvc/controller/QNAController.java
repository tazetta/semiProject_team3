package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.QnaSerivce;

@WebServlet({ "/qnaList", "/qnaListUser", "/qnaDetail", "/writeQue", "/writeAns", "/writeFormA" })
public class QNAController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String sub = req.getRequestURI().substring(req.getContextPath().length());

		String msg = (String) req.getSession().getAttribute("msg");

		System.out.println("session msg:" + msg);

		if (msg != null) {
			req.setAttribute("msg", msg);
			req.getSession().removeAttribute("msg");
		}

		QnaSerivce service = new QnaSerivce(req, resp);

		switch (sub) {
		case "/qnaList":
			System.out.println("");
			System.out.println("--고객센터 리스트(admin)--");
			service.qnaList();

			break;

		case "/qnaListUser":
			System.out.println("");
			System.out.println("--고객센터 리스트(user)--");
			service.qnaDetailUser();
			break;

		case "/qnaDetail":
			System.out.println("");
			System.out.println("--고객센터 상세보기 요청--");
			service.qnaDetail();
			break;

		case "/writeQue":
			System.out.println("");
			System.out.println("--고객센터 글쓰기 요청(user)--");
			service.writeQNA();
			break;

		case "/writeAns":
			System.out.println("");
			System.out.println("--고객센터  글쓰기 요청(admin)--");
			service.writeAnswer();
			break;

		case "/writeFormA":
			System.out.println("");
			System.out.println("--답변 form요청(admin)--");
			service.writeFormA();
			break;
		}

	}
}
