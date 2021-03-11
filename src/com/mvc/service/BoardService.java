package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.BoardDAO;
import com.mvc.dto.BoardDTO;

public class BoardService {

	HttpServletRequest req=null;
	HttpServletResponse resp=null;
	String page="";
	String msg="";
	RequestDispatcher dis = null;
	
	public BoardService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void list() throws ServletException, IOException {
		
		//String loginId = (String) req.getSession().getAttribute("loginId");
		
		//if(loginId!=null) {
		String pageParam =  req.getParameter("page");
		System.out.println("page:"+pageParam);
		//한페이지 그룹 -> 1~10번
		int group =1;
		if(pageParam!=null) {
			group = Integer.parseInt(pageParam);
		}
		BoardDAO dao = new BoardDAO();
		HashMap<String, Object> map = dao.list(group);
		BoardDTO dto = new BoardDTO();
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("list",map.get("list"));
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("boardList.jsp");
		dis.forward(req, resp);
		//}else {
			//resp.sendRedirect("index.jsp");
		//}
	}

	public void write() throws ServletException, IOException {
		
		//String loginId = (String) req.getSession().getAttribute("loginId");
		
		//if(loginId!=null) { //로그인 체크
			//FileService에 우리는 파일과 관련된 내용을 추가할 예정
			FileService upload = new FileService(req);
			BoardDTO dto = upload.regist();
			System.out.println(dto.getOriFileName()+"=>"+dto.getNewFileName());//확인
			//DB저장(작성자,제목,내용 + 파일 이름)
			BoardDAO dao = new BoardDAO();
			
			String page = "boardwriteForm.jsp";
			String msg = "글 등록에 실패하였습니다.";
			
			long idx = dao.write(dto);
			
			if(idx>0) {
				page = "/boardList";
				msg = "글 등록에 성공하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);		
		//} else {
		//	resp.sendRedirect("index.jsp");
		//}
		
	}

	public void detail() throws ServletException, IOException {
		
		//String loginId = (String) req.getSession().getAttribute("loginId");
		
		//if(loginId!=null) {
			BoardDAO dao = new BoardDAO();
			String boardIdx = req.getParameter("boardIdx");
			System.out.println("boardIdx: " +boardIdx);
			BoardDTO dto = dao.detail(boardIdx);
			System.out.println("oriFileName"+dto.getOriFileName());
			
			String page="/boardList";
			
			if(dto!=null) {	
				dao = new BoardDAO();
				//dao.upHit(idx);
				page="boarddetail.jsp";
				req.setAttribute("dto", dto );
				//req.setAttribute("loginId", loginId);
			}		
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp); 
		//}
		
		
	}

	public void del() {
		// TODO Auto-generated method stub
		
	}

	public void updateForm() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

}
