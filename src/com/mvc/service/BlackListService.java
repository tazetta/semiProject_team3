package com.mvc.service;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.BlackListDAO;
import com.mvc.dao.MemberListDAO;
import com.mvc.dto.BlackListDTO;

public class BlackListService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;
	String page = "";
	String msg = "";
	
	public BlackListService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void memberBlackAdd() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
		String managerid = req.getParameter("managerid");
		String id = req.getParameter("id");
		String reason = req.getParameter("reason");
		System.out.println(managerid+"/"+id+"/"+reason);
		
		BlackListDAO dao = new BlackListDAO();
		BlackListDTO dto = new BlackListDTO();
		
		dto.setManagerid(managerid);
		dto.setId(id);
		dto.setReason(reason);
		
		msg = "";
		page = "/memberBlackList";
		
		if(dao.memberBlackAdd(dto)) {
			page = "/memberBlackList";
			msg = "해당 회원을 블랙리스트에 추가하시겠습니까?";
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
	
	
	public void memberBlackList() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
			
		String pageParam =  req.getParameter("page");
		System.out.println("page:"+pageParam);
		//한페이지 그룹 -> 1~10번
		int group =1;
		if(pageParam!=null) {
			group = Integer.parseInt(pageParam);
		}		
		BlackListDAO dao = new BlackListDAO();
		HashMap<String, Object> map = dao.memberBlackList(group);
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("memberBlackList", map.get("memberBlackList"));
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("memberBlackList.jsp");
		dis.forward(req, resp);
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberBlackDetail() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
			String id = req.getParameter("id");
			System.out.println("상세보기 id: " + id);
			
			String memberBlackDetail = "/memberBlackList";
			String page = memberBlackDetail;
			
			BlackListDAO dao = new BlackListDAO();
			BlackListDTO dto = dao.memberBlackDetail(id);
			
			if (dto != null) {
				dao = new BlackListDAO();
				page = "memberBlackDetail.jsp";
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



}
