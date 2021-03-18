package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.MemberService;


@WebServlet({"/login","/profile","/memberUpdateForm","/memberUpdate","/pwUpdate","/wroteList","/overlay","/logout","/join","/findId","/findPw","/memberWithdraw"
	,"/visitedList","/bookmarkList","/findpwUpdate"})

public class MemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String sub = req.getRequestURI().substring(req.getContextPath().length());
		MemberService service = new MemberService(req,resp);
		
		String msg = (String) req.getSession().getAttribute("msg");

		System.out.println("session msg:"+msg);
		
		if(msg != null) { 
			req.setAttribute("msg", msg);  
			req.getSession().removeAttribute("msg"); 
		}
		
		switch (sub) {
		case"/login":
			System.out.println("");
			System.out.println("-- 로그인 요청--");
			service.login();
			break;
		case "/profile":
			System.out.println("");
			System.out.println("---회원정보 보기 요청---");
			service.profile();
			break;
			
		case "/memberUpdateForm":
			System.out.println("");
			System.out.println("-- 수정할 회원정보 보기 요청--");
			service.updateDetail();

		case "/memberUpdate":
			System.out.println("");
			System.out.println("---회원정보 수정 요청---");
			service.update();
			break;
			
		case "/pwUpdate":
			System.out.println("");
			System.out.println("---회원 비번 수정 요청----");
			service.pwUpdate();
			break;
			
		case"/wroteList":
			System.out.println("");
			System.out.println("--내가 쓴 글 보기 요청--");
			service.wroteList();
			break;
			
		case "/wroteDel":
			System.out.println("");
			System.out.println("--내가 쓴 글 삭제 요청--");
			service.wroteDel();
			
			break;
		
		case"/overlay":
			System.out.println("중복체크요청");
			service.overlay();
			break;
			
		case "/logout":
			req.getSession().removeAttribute("loginId");//로그인체크
			req.getSession().removeAttribute("isManager");
			resp.sendRedirect("index.jsp");//로그아웃시 index.jsp로
			break;
			
		case "/join":
			System.out.println("회원가입 요청");
			service.join();
			break;
			
		case "/findId":
			System.out.println("아이디 찾기 요청");
			service.findId();
			break;
			
		case "/findPw":
			System.out.println("비밀번호 찾기 요청");
			service.findPw();
			break;	
			
		case "/findpwUpdate":
			System.out.println("비밀번호 찾기 후 수정 요청");
			service.findpwUpdate();
			break;
			
		case"/memberWithdraw":
			System.out.println("");
			System.out.println("--회원탈퇴 요청--");
			service.memberWithdraw();
			break;
			
		case "/visitedList":
			System.out.println("");
			System.out.println("--가봤어요 리스트 요청--");
			service.visitedList();
			break;
			
		case "/bookmarkList":
			System.out.println("");
			System.out.println("--즐겨찾기 리스트 요청--");
			service.bookmarkList();
			
			
		
		}
		
		
	}
		
	

}
