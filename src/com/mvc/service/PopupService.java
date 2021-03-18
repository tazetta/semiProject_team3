package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.PopupDAO;
import com.mvc.dao.TripDAO;
import com.mvc.dto.PopupDTO;
import com.mvc.dto.TripDTO;

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
			PopupDAO dao = new PopupDAO();
			ArrayList<PopupDTO> popupList = dao.popupList();
			req.setAttribute("popupList", popupList);
			dis = req.getRequestDispatcher("pop.jsp");		
			dis.forward(req, resp);
	}

	public void popupWrite() throws ServletException, IOException {
			String managerid = req.getParameter("managerid");
			String subject = req.getParameter("subject");
			String content = req.getParameter("content");
			System.out.println(managerid+"/"+subject+"/"+content);
			
			PopupDTO dto = new PopupDTO();
			PopupDAO dao = new PopupDAO();
						
			dto.setManagerid(managerid);
			dto.setSubject(subject);
			dto.setContent(content);
			
			page = "/popWrite.jsp";
			msg = "팝업 등록에 실패 하였습니다.";
			
			if(dao.popupWrite(dto)) {
				page = "/popupList";		
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
		PopupDAO dao = new PopupDAO();
		PopupDTO dto = dao.detail(infoidx);
		
		if(dto != null) {
			dao = new PopupDAO();
			page = "popDetail.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);			
	}

	public void updateForm() throws ServletException, IOException {
		String infoidx = req.getParameter("infoidx");
		System.out.println("updateForm infoidx:"+infoidx);
		
		PopupDAO dao = new PopupDAO();
		PopupDTO dto = dao.detail(infoidx);
		
		page = "/popupList";
		
		if(dto!=null) {
			page="popUpdate.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	public int update() throws ServletException, IOException {
		
		int success = 0;
		String infoidx = req.getParameter("infoidx");
		String managerid = req.getParameter("managerid");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String popupalert = req.getParameter("popupalert");
		System.out.println(infoidx+"/"+managerid+"/"+subject+"/"+content+"/"+popupalert);
		
		PopupDAO dao = new PopupDAO();
		PopupDTO dto = new PopupDTO ();
		
		dto.setInfoidx(Integer.parseInt(infoidx));
		dto.setManagerid(managerid);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setPopupalert(popupalert);

		page = "/popupUpdateForm";
		msg = "팝업 수정에 실패하였습니다.";
		
		if(dao.update(dto)) {
			page="popupDetail?infoidx="+infoidx;
			msg = "수정 성공";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		return success;
	}

	public void popupDel() throws ServletException, IOException {
		String infoidx = req.getParameter("infoidx");
		System.out.println("삭제할 팝업번호 : "+infoidx);
		
		msg = "";
		page ="/popupList";
		
		PopupDAO dao = new PopupDAO();
		if(dao.popupDel(infoidx)) {	
			msg="팝업을 삭제하였습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);		
		dis.forward(req, resp);		
	}

	public void popupMain() throws ServletException, IOException {
		PopupDAO dao = new PopupDAO();
		PopupDTO dto = dao.popupMain();
		System.out.println("dto:" + dto);
		
		page = "main.jsp";
	
		if (dto != null) {
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);		
		dis.forward(req, resp);
	}

	
}