package com.mvc.service;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.dao.BoardDAO;
import com.mvc.dao.ReportDAO;
import com.mvc.dto.BoardDTO;
import com.mvc.dto.RepDTO;

public class ReportService {

	HttpServletRequest req=null;
	HttpServletResponse resp =null;
	
	RequestDispatcher dis = null;
	
	public ReportService(HttpServletRequest req, HttpServletResponse resp) {
		this.req= req;
		this.resp=resp;
	}
	
//	public void tripDetail() throws ServletException, IOException {
//		String conIdx =req.getParameter("contentId");
//		System.out.println("conIdx : "+conIdx);
//		String id = (String) req.getSession().getAttribute("loginId");
//		TripDetailDAO dao = new TripDetailDAO();		
//		TripDTO detail = dao.tripDetail(conIdx);
//		if(detail!=null) {
//			BookmarkDTO book =dao.bookmark(conIdx,id);
//			if(book!=null && id!=null) {
//				req.setAttribute("book", book);
//			}
//			book =dao.visit(conIdx,id);
//			if(book!=null&& id!=null) {
//				req.setAttribute("visit", book);
//			}
//			req.setAttribute("detail", detail);
//			req.setAttribute("conIdx", conIdx);
//		}
//		dao.resClose();
//		dis =req.getRequestDispatcher("tripDetail.jsp");
//		dis.forward(req, resp);
//		
//	}
//	
//	
//	public void addDel() throws ServletException, IOException {
//		if(req.getSession().getAttribute("loginId")!=null) {
//			String myidx = req.getParameter("myidx");
//			String deact = req.getParameter("deact");
//			String conIdx = req.getParameter("conIdx");
//			String type = req.getParameter("type");
//			
//			String id = (String) req.getSession().getAttribute("loginId");
//			System.out.println("북마크 번호 : "+myidx+"/"+deact+"/"+conIdx+"/"+type+"/"+id);
//			BookmarkDTO bdto= new BookmarkDTO();
//			if(myidx!="") {
//				bdto.setMyidx(Integer.parseInt(myidx));			
//			}
//			bdto.setDeactivate(deact);
//			bdto.setContentid(Integer.parseInt(conIdx));
//			bdto.setType(Integer.parseInt(type));
//			bdto.setId(id);
//			
//			
//			TripDetailDAO dao = new TripDetailDAO();	
//			int a =dao.addDel(bdto);
//			System.out.println("성공여부 : "+a);
//			dao.resClose();
//			resp.sendRedirect("./tripDetail?contentId="+conIdx);
//			
//		}else {
//			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
//			dis = req.getRequestDispatcher("/login.jsp");
//			dis.forward(req, resp);
//		}
//		
//	}


	

	public void updateYN() throws IOException ,ServletException {
		if(req.getSession().getAttribute("isManager")=="true") {
			String updateYN = req.getParameter("updateYN");			
			String boardIdx = req.getParameter("boardIdx");// 게시 번호
			String bbsRepIdx = req.getParameter("bbsRepIdx"); // 신고 번호 
			String type = req.getParameter("type"); //타입 1 게시물, 2 댓글
			String deactivate = req.getParameter("deactivate"); // 신고 글의 처리 여부
			String managerid = (String) req.getSession().getAttribute("loginId");
			System.out.println(updateYN+"/"+boardIdx+"/"+bbsRepIdx+"/"+type+"/"+deactivate);
			
			RepDTO dto = new RepDTO();
			dto.setUpdateYN(updateYN);
			dto.setBoardIdx(Integer.parseInt(boardIdx));
			dto.setBbsRepIdx(Integer.parseInt(bbsRepIdx));
			dto.setType(type);
			dto.setDeactivate(deactivate);
			
			ReportDAO dao = new ReportDAO();
			boolean doit = dao.tasked(dto);
			int suc =0;
			if(doit) {
				suc=dao.updateYN(dto,managerid);
				
			}
			dao.resClose();
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("suc", suc);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			resp.setHeader("Access-Control-Allow-origin", "*");
			resp.getWriter().println(json);
			
		}else {
			req.setAttribute("msg", "관리자 로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("/login.jsp");
			dis.forward(req, resp);
		}
		
	}

	public void reportComment() throws ServletException, IOException {
		if(req.getSession().getAttribute("isManager")=="true") {
			String pageParam =  req.getParameter("page");
			String deactivate = "FALSE";
			String type ="2";
			if(req.getParameter("deactivate")!=null) {
				deactivate = req.getParameter("deactivate");
				System.out.println("deactivate : "+deactivate);
			}
			System.out.println("page:"+pageParam+deactivate);
			//한페이지 그룹 -> 1~10번
			int group =1;
			if(pageParam!=null) {
				group = Integer.parseInt(pageParam);
			}
			ReportDAO dao = new ReportDAO();	
			
			HashMap<String, Object> map =dao.reportComment(group,deactivate,type);
			if(map!=null) {
				req.setAttribute("deactivate", deactivate);
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("list", map.get("list"));
				req.setAttribute("currPage", group);
				dis = req.getRequestDispatcher("commentRepList.jsp");
				dis.forward(req, resp);
			}
			dao.resClose();
			
		}else {
			req.setAttribute("msg", "관리자 로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("/login.jsp");
			dis.forward(req, resp);
		}
		
	}

	public void reportBBS() throws ServletException, IOException {
		if(req.getSession().getAttribute("isManager")=="true") {
			String pageParam =  req.getParameter("page");
			String deactivate = "FALSE";
			String type ="1";
			if(req.getParameter("deactivate")!=null) {
				deactivate = req.getParameter("deactivate");
			}
			System.out.println("page:"+pageParam+deactivate);
			//한페이지 그룹 -> 1~10번
			int group =1;
			if(pageParam!=null) {
				group = Integer.parseInt(pageParam);
			}
			ReportDAO dao = new ReportDAO();	
			
			HashMap<String, Object> map =dao.reportComment(group,deactivate,type);
			if(map!=null) {
				req.setAttribute("deactivate", deactivate);
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("list", map.get("list"));
				req.setAttribute("currPage", group);
				dis = req.getRequestDispatcher("bbsRepList.jsp");
				dis.forward(req, resp);
			}
			dao.resClose();			
		}else {
			req.setAttribute("msg", "관리자 로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("/login.jsp");
			dis.forward(req, resp);
		}
		
	}
	
	public void repDetailCom() throws ServletException, IOException {
		if(req.getSession().getAttribute("isManager")=="true") {
			String reIdx = req.getParameter("reIdx");
			String commentRepIdx = req.getParameter("commentRepIdx");
			String boardIdx = req.getParameter("boardIdx");
			String parampage = req.getParameter("page");
			int type = 2;
			System.out.println(reIdx+"/"+commentRepIdx+"/"+boardIdx+"/"+parampage);
			BoardDAO dao = new BoardDAO();
			BoardDTO dto = dao.detail(boardIdx);
			
			dao = new BoardDAO();		
			HashMap<String, Object> map = dao.comm_list(1000,boardIdx,type);
			System.out.println(dto +"/"+map);
			
			ReportDAO dao1 = new ReportDAO();
			RepDTO reason = dao1.repReason(commentRepIdx,type,reIdx);
//			String repCnt = dao1.repCnt(reIdx,type);
			String page="/reportComment";
			System.out.println("YN : "+reason.getDeactivate());
			if(dto!=null) {	
				
				page="repDetailCom.jsp";
				req.setAttribute("currPage", parampage);
//				req.setAttribute("commentRepIdx", commentRepIdx);
//				req.setAttribute("reIdx", reIdx);
//				req.setAttribute("repCnt", repCnt);
				req.setAttribute("reason", reason);
				req.setAttribute("dto", dto);
				req.setAttribute("list", map.get("list"));
			}		
			dao1.resClose();
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp); 
			
		}else {
			req.setAttribute("msg", "관리자 로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("/login.jsp");
			dis.forward(req, resp);
		}
		
	}

	public void repDetail() throws ServletException, IOException {
		if(req.getSession().getAttribute("isManager")=="true") {
			BoardDAO dao = new BoardDAO();
			String reIdx = req.getParameter("reIdx");
			String parampage = req.getParameter("page");
			String boardIdx = req.getParameter("boardIdx");
			String bbsRepIdx = req.getParameter("bbsRepIdx");
			int type =1;
			System.out.println("boardIdx: " +boardIdx+"/"+bbsRepIdx+"/"+parampage);
			
			
			BoardDTO dto = dao.detail(boardIdx);
			
			dao = new BoardDAO();		
			HashMap<String, Object> map = dao.comm_list(1000,boardIdx,type+1);// 타입이 2가 되게 
			System.out.println(dto +"/"+map);
			
			ReportDAO dao1 = new ReportDAO();
			RepDTO reason = dao1.repReason(bbsRepIdx,type,boardIdx);
//			String repCnt = dao1.repCnt(boardIdx,type);
			String page="/reportBBS";
			
			if(dto!=null) {	
				page="repDetail.jsp";
				req.setAttribute("currPage", parampage);
//				req.setAttribute("bbsRepIdx", bbsRepIdx);
//				req.setAttribute("repCnt", repCnt);
				req.setAttribute("reason", reason);
				req.setAttribute("dto", dto);
				req.setAttribute("list", map.get("list"));
			}		
			dao1.resClose();
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp); 			
		}else {
			req.setAttribute("msg", "관리자 로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("/login.jsp");
			dis.forward(req, resp);
		}
		
	}

	
	

}
