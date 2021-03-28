package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.PopupDAO;

import com.mvc.dto.BoardDTO;

import com.mvc.dto.PopupDTO;


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
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
			String pageParam = req.getParameter("page");
			System.out.println("page:" + pageParam);
			// 한페이지 그룹 -> 1~10번
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			
			PopupDAO dao = new PopupDAO();
			HashMap<String, Object> map = dao.popupList(group);
			
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("popupList", map.get("popupList"));
			req.setAttribute("currPage", group);
			dis = req.getRequestDispatcher("pop.jsp");
			dis.forward(req, resp);
			dao.resClose();
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void popupWrite() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
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
			dao.resClose();
			
		}else {
			
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
		
		}
	
	public void detail() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
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
			dao.resClose();
			
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void updateForm() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
			
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
			dao.resClose();
			
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}
	
	public void update() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");
		if (isManager=="true") {
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
				msg = "해당 팝업을 수정하였습니다.";
				page="popupDetail?infoidx="+infoidx;
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			dao.resClose();
			
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void popupDel() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
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
			dao.resClose();
			
		}else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void popupMain() throws ServletException, IOException {
		PopupDAO dao = new PopupDAO();
		PopupDTO dto = dao.popupMain();
		System.out.println("dto:" + dto);
		dao.resClose();
		BoardService bss = new BoardService(req, resp);
		ArrayList<BoardDTO> list =bss.mainBoardList();
		page = "main.jsp";
			req.setAttribute("list", list);
			req.setAttribute("dto", dto);
		
		dis = req.getRequestDispatcher(page);		
		dis.forward(req, resp);
		
	}

	
}