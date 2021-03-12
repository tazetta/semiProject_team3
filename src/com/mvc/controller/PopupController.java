package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.PopupService;

@WebServlet({"/popupList","/popupWrite","/popupUpdate","/popupUpdateForm","/popupDel","/popupDetail","/main"})
public class PopupController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String sub = uri.substring(ctx.length());
		
		PopupService service = new PopupService(req,resp);
		req.setCharacterEncoding("utf-8");
		
		switch(sub) {
		
		case "/popupList":
			System.out.println("팝업 목록 요청");
			service.popupList();
			break;
		
		case "/popupWrite":
			System.out.println("팝업 등록 요청");
			service.popupWrite();
			break;
			
		case "/popupDetail":
			System.out.println("팝업 상세보기 요청");
			service.detail();
			break;
			
		case "/popupUpdateForm":
			System.out.println("팝업 수정보기 요청");
			service.updateForm();
			break;
			
		case "/popupUpdate":
			System.out.println("팝업 수정 요청");
			service.update();
			break;
		
		case "/popupDel":
			System.out.println("팝업 삭제 요청");
			service.popupDel();
			break;
			
		case "/main":
			System.out.println("팝업 노출 요청");
			service.popupMain();
			break;
		
		}
	}

	
}
