package com.mvc.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.TestDAO;
import com.mvc.dto.TestBookDTO;
import com.mvc.dto.TripDTO;

public class TestService {

	HttpServletRequest req=null;
	HttpServletResponse resp =null;
	
	RequestDispatcher dis = null;
	
	public TestService(HttpServletRequest req, HttpServletResponse resp) {
		this.req= req;
		this.resp=resp;
	}
	
	public void tripDetail() throws ServletException, IOException {
		String conIdx =req.getParameter("contentId");
		System.out.println("dddd : "+conIdx);
//		String conIdx ="745873";
		//id 가져와야함
		String id = "test";
		TestDAO dao = new TestDAO();		
		TripDTO detail = dao.tripDetail(conIdx);
		if(detail!=null) {
			TestBookDTO book =dao.bookmark(conIdx,id);
			if(book!=null) {
				req.setAttribute("book", book);
			}
			book =dao.visit(conIdx,id);
			if(book!=null) {
				req.setAttribute("visit", book);
			}
			req.setAttribute("detail", detail);
			req.setAttribute("conIdx", conIdx);
		}
		dao.resClose();
		dis =req.getRequestDispatcher("tripDetail.jsp");
		dis.forward(req, resp);
		
	}
	
	
	public void addDel() throws ServletException, IOException {
		//비회원은 선택 ㄴㄴ 
		String myidx = req.getParameter("myidx");
		String deact = req.getParameter("deact");
		String conIdx = req.getParameter("conIdx");
		String type = req.getParameter("type");
		//아이디 받아와야함
		String id = "test";
		System.out.println("북마크 번호 : "+myidx+"/"+deact+"/"+conIdx+"/"+type+"/"+id);
		TestBookDTO bdto= new TestBookDTO();
		if(myidx!="") {
			bdto.setMyidx(Integer.parseInt(myidx));			
		}
		bdto.setDeactivate(deact);
		bdto.setContentid(Integer.parseInt(conIdx));
		bdto.setType(Integer.parseInt(type));
		bdto.setId(id);
		
		
		TestDAO dao = new TestDAO();	
		int a =dao.addDel(bdto);
		System.out.println("성공여부 : "+a);
		dao.resClose();
		resp.sendRedirect("./tripDetail?contentId="+conIdx);
		
	}

	public void reportBBS() throws ServletException, IOException {
		
		TestDAO dao = new TestDAO();	
		
		dao.reportBBS();
	}

	

}
