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
	
	public void popupList() throws ServletException, IOException{
			popupDAO dao = new popupDAO();
			ArrayList<popupDTO> popupList = dao.popupList();
			req.setAttribute("popupList", popupList);
			dis = req.getRequestDispatcher("pop.jsp");		
			dis.forward(req, resp);
	}

	public void popupWrite() throws ServletException, IOException {
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
			
			if(dao.popupWrite(dto)) {
				page = "popupList";		
				msg = "팝업 등록에 성공 하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);		
			dis.forward(req, resp);					
		}
	
	public void detail() throws ServletException, IOException {
		
		String infoidx = req.getParameter("infoidx");
		System.out.println("상세보기 idx:"+infoidx);
		
		String detail = "/popupList";
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
		
		page = "/popupList";
		
		if(dto!=null) {
			page="pop_update.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	
	public void update() throws ServletException, IOException {
		
		String infoidx = req.getParameter("infoidx");
		String managerid = req.getParameter("managerid");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String popupalert = req.getParameter("popupalert");
		System.out.println(managerid+"/"+subject+"/"+content+"/"+popupalert);
		
//		popupDAO dao = new popupDAO();
//		popupDTO dto = new popupDTO ();
//		
//		dto.setManagerid(managerid);
//		dto.setSubject(subject);
//		dto.setContent(content);
//		dto.setPopupalert(popupalert);
//		dao.update(dto);
//	
//		page = "pop_update.jsp";
//		msg = "팝업 수정에 실패하였습니다.";
//		
//		if(dto!=null) {
//			page="pop_detail.jsp";
//			req.setAttribute("msg", msg);
//		}
//		dis = req.getRequestDispatcher(page);
//		dis.forward(req, resp);
		
		popupDAO dao = new popupDAO();
		int success = dao.update(subject,content,popupalert,infoidx);
		System.out.println("수정 성공 여부:"+success);
		
		page="/popupUpdateForm";
		msg = "팝업 수정에 실패하였습니다.";
		
		if(success>0) {
			page="/detail?infoidx="+infoidx;
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);		
		dis.forward(req, resp);	
		}
	

	public void popupDel() throws ServletException, IOException {
		String infoidx = req.getParameter("infoidx");
		System.out.println("삭제할 팝업번호 : "+infoidx);
		
		msg = "";
		page ="/popupList";
		
		popupDAO dao = new popupDAO();
		if(dao.popupDel(infoidx)) {	
			msg="팝업을 삭제하였습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);		
		dis.forward(req, resp);		
	}


}