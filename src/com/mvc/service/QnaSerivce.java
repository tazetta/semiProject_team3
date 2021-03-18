package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.QnaDAO;
import com.mvc.dto.QnaDTO;

public class QnaSerivce {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;

	QnaDAO dao = null;

	String msg = "";
	String page = "";

	RequestDispatcher dis = null;

	public QnaSerivce(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		dao = new QnaDAO();

	}

	private boolean isManager() {
		return (String) req.getSession().getAttribute("isManager") != null;
	}
	
	/* 고객센터  리스트 */
	public void qnaList() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId + "의 고객센터 게시글");
		
		String pageParam = req.getParameter("page"); 
		System.out.println("pageParam:" + pageParam);

		int group = 1; 

		if (pageParam != null) {
			group = Integer.parseInt(pageParam); 
		}
		
		if (loginId != null) { // 로그인체크
			HashMap<String, Object> map = dao.qnaList(loginId,group);
			req.setAttribute("list", map.get("list")); 
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("currPage", group);
			System.out.println("currPage:"+group);
			System.out.println("list:"+map.get("list"));

			dis = req.getRequestDispatcher("qnaList.jsp");
			dis.forward(req, resp);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}
	

	/*고객센터 리스트(사용자)*/
	public void qnaListUser() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId + "의 고객센터 게시글");
		
		String pageParam = req.getParameter("page"); 
		System.out.println("pageParam:" + pageParam);

		int group = 1; 

		if (pageParam != null) {
			group = Integer.parseInt(pageParam); 
		}
		
		if (loginId != null) { // 로그인체크
			HashMap<String, Object> map = dao.qnaListUser(loginId,group);
			req.setAttribute("list", map.get("list")); 
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("currPage", group);
			System.out.println("currPage:"+group);
			System.out.println("list:"+map.get("list"));

			dis = req.getRequestDispatcher("qnaListUser.jsp");
			dis.forward(req, resp);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
		
	}

	/*고객센터 글쓰기(관리자)*/
	public void writeAnswer() throws IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String qnaIdx = req.getParameter("qnaIdx");
		System.out.println(loginId + "의 답변 -> "+qnaIdx+":"+subject+":"+content);
		
		if (loginId != null) { // 로그인체크
			QnaDTO dto = new QnaDTO();
			dto.setQnaIdx(Integer.parseInt(qnaIdx));
			dto.setSubjectA(subject);
			dto.setContentA(content);
			dto.setManagerid(loginId);
			boolean success =dao.writeAnswer(dto);
			msg="글 등록에 실패했습니다";
			page="qnaList";
			if(success) {
				msg="글 등록에 성공 했습니다";
			}
			req.getSession().setAttribute("msg", msg); 
			resp.sendRedirect(page);
		} else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
		
	}
	

	/*고객센터 글쓰기(사용자) */
	public void writeQNA() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		System.out.println(loginId + "의 질문 -> "+subject+":"+content);
		if (loginId != null) { // 로그인체크
			QnaDTO dto = new QnaDTO();
			dto.setId(loginId);
			dto.setSubject(subject);
			dto.setContent(content);
			boolean success =dao.writeQNA(dto);
			System.out.println("고객센터 글 등록:"+success);
			msg="글 등록에 실패했습니다";
			page="qnaListUser";
			if(success) {
				msg="글 등록에 성공 했습니다";
			}
			req.getSession().setAttribute("msg", msg); 
			resp.sendRedirect(page);
		}else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
	}

	/*고객센터 상세보기*/
	public void qnaDetail() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String  qnaIdx = req.getParameter("qnaIdx");
		
		System.out.println(loginId + " 고객센터 상세보기 -"+qnaIdx);
		if (loginId != null) { // 로그인체크
			QnaDTO dto = dao.qnaDetail(loginId,qnaIdx);
			System.out.println("dto:"+dto);
			msg="상세보기에 실패했습니다";
			page="/qnaListUser";
			if(isManager()) {
				page="/qnaList";
			}
			if(dto!=null) {
				msg="";
				page="qnaDetail.jsp";
				req.setAttribute("dto", dto);
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}

	}

	/*고객센터 답변form*/
	public void writeFormA() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String  qnaIdx = req.getParameter("qnaIdx");
		
		System.out.println(loginId + "의 고객센터 답변 -"+qnaIdx);
		if (loginId != null) { // 로그인체크
			QnaDTO dto = dao.qnaDetail(loginId,qnaIdx);
			System.out.println("dto:"+dto);
			msg="상세보기에 실패했습니다";
			page="/qnaList";
			if(dto!=null) {
				msg="";
				page="writeFormA.jsp";
				req.setAttribute("dto", dto);
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}		
	}

	/*답변확인*/
	public void ansDetail() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String qnaIdx =req.getParameter("qnaIdx");
		System.out.println(loginId+" 답변확인: "+qnaIdx);
		if (loginId != null) { // 로그인체크
			QnaDTO dto = dao.qnaDetail(loginId, qnaIdx);
			System.out.println("dto:"+dto);
			msg="답변보기에 실패했습니다";
			page="/qnaListUser";
			if(isManager()) {
				page="/qnaList";
			}
			if(dto!=null) {
				msg="";
				page="ansDetail.jsp";
				req.setAttribute("dto", dto);
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else{
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
		
	}
	
	

	/* 고객센터 게시글 삭제*/
	public void qnaDel() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String qnaIdx =req.getParameter("qnaIdx");
		System.out.println(loginId+" 의 게시글 삭제: "+qnaIdx);
		if (loginId != null) { // 로그인체크
			boolean success =dao.qnaDel(qnaIdx);
			msg="삭제에 실패했습니다";
			page="/qnaListUser";
			if(isManager()) {
				page="/qnaList";
			}
			if(success) {
				msg="삭제에 성공했습니다";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			
		}else {
			msg="로그인이 필요한 서비스 입니다";
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("index.jsp"); 
		}
		
	}

		
	

}
