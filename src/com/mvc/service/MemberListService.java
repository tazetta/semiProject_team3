package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.MemberListDAO;
import com.mvc.dao.PopupDAO;
import com.mvc.dto.MemberListDTO;

public class MemberListService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;
	String page = "";
	String msg = "";

	public MemberListService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void memberList() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
			
		String pageParam =  req.getParameter("page");
		System.out.println("page:"+pageParam);
		//한페이지 그룹 -> 1~10번
		int group =1;
		if(pageParam!=null) {
			group = Integer.parseInt(pageParam);
		}		
		MemberListDAO dao = new MemberListDAO();
		HashMap<String, Object> map = dao.memberList(group);
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("memberList", map.get("memberList"));
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("memberList.jsp");
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDetail() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
		String id = req.getParameter("id");
		System.out.println("상세보기 id: " + id);

		String memberDetail = "/memberList";
		String page = memberDetail;

		MemberListDAO dao = new MemberListDAO();
		MemberListDTO dto = dao.memberDetail(id);

		if (dto != null) {
			dao = new MemberListDAO();
			page = "memberDetail.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDelList() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
			String pageParam =  req.getParameter("page");
			System.out.println("page:"+pageParam);
			//한페이지 그룹 -> 1~10번
			int group =1;
			if(pageParam!=null) {
				group = Integer.parseInt(pageParam);
		}				
		MemberListDAO dao = new MemberListDAO();
		HashMap<String, Object> map  = dao.memberDelList(group);
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("memberDelList", map.get("memberDelList"));
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("memberDelList.jsp");
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDelDetail() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
		String id = req.getParameter("id");
		System.out.println("탈퇴회원 상세보기 id: " + id);

		String memberDelDetail = "/memberDetail";
		String page = memberDelDetail;

		MemberListDAO dao = new MemberListDAO();
		MemberListDTO dto = dao.memberDetail(id);

		if (dto != null) {
			dao = new MemberListDAO();
			page = "memberDelDetail.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDraw() throws ServletException, IOException { // 탈퇴 회원 삭제

		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
		String id = req.getParameter("id");
		System.out.println("삭제할 탈퇴회원 id: " + id);

		msg = "";
		page = "/memberDelList";

		MemberListDAO dao = new MemberListDAO();
		if (dao.memberDraw(id)) {
			msg = "해당 회원을 삭제하였습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberRestore() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
		String id = req.getParameter("id");
		System.out.println("복구할 탈퇴회원 id: " + id);

		msg = "";
		page = "/memberDelList";

		MemberListDAO dao = new MemberListDAO();
		if (dao.memberRestore(id)) {
			msg = "해당 회원을 복구하였습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberSearch() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
		MemberListDAO dao = new MemberListDAO();
		ArrayList<MemberListDTO> memberSearch = dao.memberSearch();
		req.setAttribute("memberSearch", memberSearch);
		dis = req.getRequestDispatcher("memberList.jsp");
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

}
