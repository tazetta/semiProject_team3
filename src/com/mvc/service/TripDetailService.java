package com.mvc.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.dao.BoardDAO;
import com.mvc.dao.TripDetailDAO;
import com.mvc.dto.BoardDTO;
import com.mvc.dto.CommentDTO;
import com.mvc.dto.RepDTO;
import com.mvc.dto.BookmarkDTO;
import com.mvc.dto.TripDTO;

public class TripDetailService {

	HttpServletRequest req=null;
	HttpServletResponse resp =null;
	
	RequestDispatcher dis = null;
	
	public TripDetailService(HttpServletRequest req, HttpServletResponse resp) {
		this.req= req;
		this.resp=resp;
	}
	
	public void tripDetail() throws ServletException, IOException {
		String conIdx =req.getParameter("contentId");
		System.out.println("conIdx : "+conIdx);
		String id = (String) req.getSession().getAttribute("loginId");
		TripDetailDAO dao = new TripDetailDAO();		
		TripDTO detail = dao.tripDetail(conIdx);
		if(detail!=null) {
			BookmarkDTO book =dao.bookmark(conIdx,id);
			if(book!=null && id!=null) {
				req.setAttribute("book", book);
			}
			book =dao.visit(conIdx,id);
			if(book!=null&& id!=null) {
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

		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {

			String myidx = req.getParameter("myidx");
			String deact = req.getParameter("deact");
			String conIdx = req.getParameter("conIdx");
			String type = req.getParameter("type");
			
			String id = (String) req.getSession().getAttribute("loginId");
			System.out.println("북마크 번호 : "+myidx+"/"+deact+"/"+conIdx+"/"+type+"/"+id);
			BookmarkDTO bdto= new BookmarkDTO();
			if(myidx!="") {
				bdto.setMyidx(Integer.parseInt(myidx));			
			}
			bdto.setDeactivate(deact);
			bdto.setContentid(Integer.parseInt(conIdx));
			bdto.setType(Integer.parseInt(type));
			bdto.setId(id);
			
			
			TripDetailDAO dao = new TripDetailDAO();	
			int a =dao.addDel(bdto);
			System.out.println("성공여부 : "+a);
			dao.resClose();
			resp.sendRedirect("./tripDetail?contentId="+conIdx);
			
		}else {

			req.setAttribute("msg", "로그인이 필요한 서비스입니다.");
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
		
	}


	

}
