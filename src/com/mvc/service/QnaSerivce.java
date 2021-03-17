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
			msg="로그인 후 이용해주세요";
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
			page="qnaList";
			if(success) {
				msg="글 등록에 성공 했습니다";
			}
			req.getSession().setAttribute("msg", msg); 
			resp.sendRedirect(page);
		}else {
			msg="로그인 후 이용해주세요";
			req.getSession().setAttribute("msg", msg); 
			resp.sendRedirect("index.jsp");
		}
	}

	/*고객센터 상세보기*/
	public void qnaDetail() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String  qnaIdx = req.getParameter("qnaIdx");
		
		System.out.println(loginId + "의 고객센터 상세보기 -"+qnaIdx);
		if (loginId != null) { // 로그인체크
			QnaDTO dto = dao.qnaDetail(loginId,qnaIdx);
			System.out.println("dto:"+dto);
			msg="상세보기에 실패했습니다";
			page="/qnaList";
			if(dto!=null) {
				msg="";
				page="qnaDetail.jsp";
				req.setAttribute("dto", dto);
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else {
			msg="로그인 후 이용해주세요";
			req.getSession().setAttribute("msg", msg); 
			resp.sendRedirect("index.jsp");
		}
		
		
		
	}
		
	

}
