package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.popupDAO;
import com.mvc.dto.popupDTO;

public class PopupService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;
	String page = "";
	String msg = "";
	
	public PopupService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	public void list() throws ServletException, IOException{
			popupDAO dao = new popupDAO();
			ArrayList<popupDTO> list = dao.list();
			req.setAttribute("list", list);
			dis = req.getRequestDispatcher("pop.jsp");		
			dis.forward(req, resp);
	}

	public void write() throws ServletException, IOException {
			String managerid = req.getParameter("managerid");
			String subject = req.getParameter("subject");
			String content = req.getParameter("content");
			String popupalert = req.getParameter("popupalert");
			System.out.println(managerid+"/"+subject+"/"+content+"/"+popupalert);
			
			popupDTO dto = new popupDTO();
			popupDAO dao = new popupDAO();
			
			dto.setManagerid(managerid);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPopupalert(popupalert);
			
			page = "pop_write.jsp";
			msg = "팝업 등록에 실패 하였습니다.";
			
			if(dao.write(dto)) {
				page = "pop.jsp";		
				msg = "팝업 등록에 성공 하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);		
			dis.forward(req, resp);					
		}
	
	public void detail() throws ServletException, IOException {
		
		String infoidx = req.getParameter("infoidx");
		System.out.println("상세보기 idx:"+infoidx);
		
		String detail = "/list";
		String page = detail;
		popupDAO dao = new popupDAO();
		popupDTO dto = dao.detail(infoidx);
		
		if(dto != null) {
			dao = new popupDAO();
			page = "pop_detail.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);			
	}
	

	public void updateForm() throws ServletException, IOException {
		String infoidx = req.getParameter("infoidx");
		System.out.println("updateForm infoidx:"+infoidx);
		
		popupDAO dao = new popupDAO();
		popupDTO dto = dao.detail(infoidx);
		
		page = "/list";
		
		if(dto!=null) {
			page="pop_update.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	
	public void update() throws ServletException, IOException {
		
		String managerid = req.getParameter("managerid");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String popupalert = req.getParameter("popupalert");
		
		System.out.println(managerid+"/"+subject+"/"+content+"/"+popupalert);
		popupDTO dto = new popupDTO ();
		popupDAO dao = new popupDAO();
		dao.update(dto);
		resp.sendRedirect("detail?infoidx="+dto.getInfoidx());
		}
	

	public void del() throws ServletException, IOException {
		String infoidx = req.getParameter("infoidx");
		System.out.println("삭제할 팝업번호 : "+infoidx);
		
		msg = "";
		
		popupDAO dao = new popupDAO();
		if(dao.del(infoidx)) {	
			msg="팝업을 삭제하였습니다.";
			page ="./list";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);		
		dis.forward(req, resp);		
	}


}