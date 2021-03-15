package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;

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
		MemberListDAO dao = new MemberListDAO();
		ArrayList<MemberListDTO> memberList = dao.memberList();
		req.setAttribute("memberList", memberList);
		dis = req.getRequestDispatcher("memberList.jsp");
		dis.forward(req, resp);
	}

	public void memberDetail() throws ServletException, IOException {
		
		String id = req.getParameter("id");
		System.out.println("상세보기 id: "+id);
		
		String memberDetail = "/memberList";
		String page = memberDetail;
		
		MemberListDAO dao = new MemberListDAO();
		MemberListDTO dto = dao.memberDetail(id);
		
		if(dto != null) {
			dao = new MemberListDAO();
			page = "memberDetail.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);			
	}
		
	

}
