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
import com.mvc.dao.TestDAO;
import com.mvc.dto.BoardDTO;
import com.mvc.dto.CommentDTO;
import com.mvc.dto.RepDTO;
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
		String id = (String) req.getSession().getAttribute("loginId");
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
		String id = (String) req.getSession().getAttribute("loginId");
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
		String pageParam =  req.getParameter("page");
		String deactivate = "FALSE";
		if(req.getParameter("deactivate")!=null) {
			deactivate = req.getParameter("deactivate");
		}
		System.out.println("page:"+pageParam+deactivate);
		//한페이지 그룹 -> 1~10번
		int group =1;
		if(pageParam!=null) {
			group = Integer.parseInt(pageParam);
		}
		TestDAO dao = new TestDAO();	
		
		HashMap<String, Object> map =dao.reportBBS(group,deactivate);
		if(map!=null) {
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("list", map.get("list"));
			req.setAttribute("currPage", group);
			dis = req.getRequestDispatcher("bbsRepList.jsp");
			dis.forward(req, resp);
		}
		dao.resClose();
	}

	public void repDetail() throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		String boardIdx = req.getParameter("boardIdx");
		String bbsRepIdx = req.getParameter("bbsRepIdx");
		int type =1;
		System.out.println("boardIdx: " +boardIdx+"/"+bbsRepIdx);
		BoardDTO dto = dao.detail(boardIdx);
		
		dao = new BoardDAO();		
		ArrayList<CommentDTO> list = dao.comm_list(boardIdx);
		System.out.println(dto +"/"+list);
		
		TestDAO dao1 = new TestDAO();
		String reason = dao1.repReason(bbsRepIdx,type);
		String repCnt = dao1.repCnt(boardIdx,type);
		String page="/reportBBS";
		
		if(dto!=null) {	
			dao = new BoardDAO();
			page="repDetail.jsp";
			req.setAttribute("bbsRepIdx", bbsRepIdx);
			req.setAttribute("repCnt", repCnt);
			req.setAttribute("reason", reason);
			req.setAttribute("dto", dto);
			req.setAttribute("list", list);
		}		
		dao1.resClose();
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp); 
		
	}

	public void updateYN() throws IOException ,ServletException {
		String updateYN = req.getParameter("updateYN");
		String boardIdx = req.getParameter("boardIdx");
		String bbsRepIdx = req.getParameter("bbsRepIdx");
		System.out.println(updateYN+"/"+boardIdx+"/"+bbsRepIdx);
		
		TestDAO dao = new TestDAO();
		int suc=(dao.updateYN(updateYN,boardIdx,bbsRepIdx));
		dao.resClose();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("suc", suc);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		resp.setHeader("Access-Control-Allow-origin", "*");
		resp.getWriter().println(json);
		
	}

	public void reportComment() throws ServletException, IOException {
		String pageParam =  req.getParameter("page");
		String deactivate = "FALSE";
		if(req.getParameter("deactivate")!=null) {
			deactivate = req.getParameter("deactivate");
		}
		System.out.println("page:"+pageParam+deactivate);
		//한페이지 그룹 -> 1~10번
		int group =1;
		if(pageParam!=null) {
			group = Integer.parseInt(pageParam);
		}
		TestDAO dao = new TestDAO();	
		
		HashMap<String, Object> map =dao.reportComment(group,deactivate);
		if(map!=null) {
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("list", map.get("list"));
			req.setAttribute("currPage", group);
			dis = req.getRequestDispatcher("commentRepList.jsp");
			dis.forward(req, resp);
		}
		dao.resClose();
		
	}

	public void repDetailCom() throws ServletException, IOException {
		String reIdx = req.getParameter("reIdx");
		String commentRepIdx = req.getParameter("commentRepIdx");
		int type = 2;
		System.out.println(reIdx+"/"+commentRepIdx);
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.detail(commentRepIdx);
		
		dao = new BoardDAO();		
		ArrayList<CommentDTO> list = dao.comm_list(reIdx);
		System.out.println(dto +"/"+list);
		
		TestDAO dao1 = new TestDAO();
		String reason = dao1.repReason(commentRepIdx,type);
		String repCnt = dao1.repCnt(reIdx,type);
		String page="/reportBBS";
		
		if(dto!=null) {	
			dao = new BoardDAO();
			page="repDetail.jsp";
			req.setAttribute("commentRepIdx", commentRepIdx);
			req.setAttribute("repCnt", repCnt);
			req.setAttribute("reason", reason);
			req.setAttribute("dto", dto);
			req.setAttribute("list", list);
		}		
		dao1.resClose();
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp); 
		
	}
	

	

}
