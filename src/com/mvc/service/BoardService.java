package com.mvc.service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.dao.BoardDAO;
import com.mvc.dto.BoardDTO;
import com.mvc.dto.CommentDTO;


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
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
		String pageParam =  req.getParameter("page");
		System.out.println("page:"+pageParam);
		//한페이지 그룹 -> 1~10번
		int group =1;
		if(pageParam!=null) {
			group = Integer.parseInt(pageParam);
		}
		BoardDAO dao = new BoardDAO();
		HashMap<String, Object> map = dao.list(group);
		dao = new BoardDAO();
		ArrayList<BoardDTO> managerbbsList = dao.managerbbsList();
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("list",map.get("list"));
		req.setAttribute("currPage", group);
		req.setAttribute("managerbbsList", managerbbsList);
		dis = req.getRequestDispatcher("boardList.jsp");
		dis.forward(req, resp);
		}else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
	}

	public void write() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) { //로그인 체크
			//FileService에 우리는 파일과 관련된 내용을 추가할 예정
			FileService upload = new FileService(req);
			BoardDTO dto = upload.regist();
			System.out.println(dto.getOriFileName()+"=>"+dto.getNewFileName());//확인
			//DB저장(작성자,제목,내용 + 파일 이름)
			BoardDAO dao = new BoardDAO();
			
			page = "boardwriteForm.jsp";
			msg = "글 등록에 실패하였습니다.";
			int currPage =1;
			long boardIdx = dao.write(dto);
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("boardIdx",boardIdx);
//			Gson gson = new Gson();
//			String json = gson.toJson(map);
//			System.out.println(json);
//			resp.setContentType("text/html; charset=UTF-8");
//			resp.setHeader("Access-Control-Allow-origin", "*");
//			resp.getWriter().print(json);
			if(boardIdx>0) {
				page = "boardDetail?boardIdx="+boardIdx+"&page="+currPage;
				msg = "글 등록에 성공하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);		
		} else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
		
	}

	public void detail() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
			String boardIdx = req.getParameter("boardIdx");
			String currPage = req.getParameter("page");
			String boardkeyword = req.getParameter("boardkeyword");
			String searchType = req.getParameter("searchType");
			System.out.println("검색페이지로 돌아갈거니? :"+ boardkeyword);
			System.out.println("현재페이지: "+currPage);
			System.out.println("boardIdx: " +boardIdx);
			BoardDAO dao = new BoardDAO();
			BoardDTO dto = dao.detail(boardIdx);
			System.out.println("관리자인가? : "+dto.getIsManager());
			System.out.println("oriFileName"+dto.getOriFileName());
			System.out.println("비활성화상태:"+dto.getDeactivate());
			
			//댓글페이징
			//String ajax = req.getParameter("type");
			String pageParam =  req.getParameter("commpage");
			System.out.println("page:"+pageParam);
			int group =1;
			if(pageParam!=null) {
				group = Integer.parseInt(pageParam);
				if(group>1) {
					dao = new BoardDAO();
					dao.upDown(boardIdx);
				}
			}
			dao = new BoardDAO();
			int type = 1;
			HashMap<String, Object> map = dao.comm_list(group,boardIdx,type);
			System.out.println("댓글리스트 사이즈: "+map.get("maxPage"));
			System.out.println("댓글 페이지 : "+ group);
			map.put("commcurrPage",group);
			String page="/boardList?page="+currPage;
			String url = "searchType=" + searchType + "&boardkeyword=" + boardkeyword;
			if(dto!=null && dto.getDeactivate().equals("FALSE")) {	
				dao = new BoardDAO();
				dao.upHit(boardIdx);
				page="boarddetail.jsp";
				req.setAttribute("url", url);
				req.setAttribute("boardkeyword", boardkeyword);
				req.setAttribute("currPage", currPage);
				req.setAttribute("dto", dto);
				req.setAttribute("list", map.get("list"));
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("commcurrPage", group);
				/*
				 * Gson gson = new Gson(); String json = gson.toJson(map);
				 * resp.setContentType("text/html; charset=UTF-8");
				 * resp.setHeader("Access-Control-Allow-origin", "*");
				 * resp.getWriter().println(json);
				 */
			}		
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp); 
		}else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
	}

	public void del() throws IOException, ServletException {//비활성화
		String loginId = (String) req.getSession().getAttribute("loginId");
		String isManager = (String) req.getSession().getAttribute("isManager");
		String boardIdx = req.getParameter("boardIdx");
		String id = req.getParameter("id");
		if(loginId.equals(id) || isManager.equals("true")) {//작성자와 로그인아이디가 같거나 관리자 이면
		System.out.println("delete idx : "+boardIdx);
		System.out.println("삭제할 글 작성자 아이디:"+id);	
		//FileService upload = new FileService(req);

		BoardDAO dao = new BoardDAO();
		String newFileName = dao.getFileName(boardIdx);//파일명추출

		dao = new BoardDAO();
		msg="삭제 실패했습니다.";
		page="boardList";
		if(dao.del(boardIdx,newFileName)>0) {
			msg="삭제가 완료되었습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
//		if(success>0 && newFileName!=null) {//비활성화해도 파일은 남아있게 삭제하는부분 주석 처리
//			System.out.println("파일 삭제");
//			upload.delete(newFileName);
//		}
		
		}
	}

	public void updateForm() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		String id = req.getParameter("id");
		String boardIdx = req.getParameter("boardIdx");
        System.out.println("수정할 아이디와 로그인 아이디 : "+ loginId+"/"+id);
		if(loginId.equals(id)) {
			String currPage = req.getParameter("page");
			BoardDAO dao = new BoardDAO();
			BoardDTO dto = dao.detail(boardIdx);
			page = "/boardList?page="+currPage;
			if(loginId.equals(id) && dto.getDeactivate().equals("FALSE")) {//로그인아이디와 작성자 아이디가 같고 비활성화상태가 아니면
				page="boardUpdateForm.jsp";
				req.setAttribute("page", currPage);
				req.setAttribute("dto", dto);
			}		
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);			
		} else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
		
	}

	public void update() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
			String currPage = req.getParameter("page");
			System.out.println("수정후페이지:"+currPage);
			FileService upload = new FileService(req);
			BoardDTO dto = upload.regist();
			System.out.println(dto.getOriFileName()+"/"+dto.getNewFileName());
			BoardDAO dao = new BoardDAO();
			msg="수정에 실패했습니다.";
			if(loginId.equals(dto.getId())&&dao.update(dto)>0) {
				msg="수정이 완료되었습니다";
			}
			
			if(dto.getOriFileName()!=null) {
				//업로드 파일이 있다면 기존파일 지우기, 새로운 내용을 photo에 update
				int boardIdx= dto.getBoardIdx();
				dao = new BoardDAO();
				String delFileName = dao.getFileName(String.valueOf(boardIdx));
				System.out.println("삭제할 파일명: "+ delFileName);
				dao = new BoardDAO();
				dao.updateFileName(delFileName, dto);
				
//				//파일삭제
//				if(delFileName!=null) {
//					upload.delete(delFileName);				
//				}
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("boardDetail?boardIdx="+dto.getBoardIdx()+"&page="+currPage);
			dis.forward(req, resp);
		} else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
	}

	public void commentWrite() throws ServletException, IOException {
		String comment = req.getParameter("comment");
		String boardIdx = req.getParameter("boardIdx");
		String currPage = req.getParameter("page");
		System.out.println("댓글내용:"+comment);
		System.out.println("boardIdx:"+boardIdx);
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		if(loginId!=null) {
			BoardDAO dao = new BoardDAO();
			dao.upDown(boardIdx); //댓글등록할때도 조회수가 올라가버려서
			page="boardDetail?page="+currPage;
			msg="댓글등록에 실패하였습니다.";
			dao = new BoardDAO();
			if(dao.commentWrite(boardIdx,comment,loginId)) {
				msg="댓글이 등록되었습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			
		}else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
		
	}

	public void commentUpdateForm() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String id = req.getParameter("id");
		String reIdx = req.getParameter("reIdx");
		String boardIdx = req.getParameter("boardIdx");
		String currPage = req.getParameter("page");
		System.out.println(id+"/"+reIdx+"/"+boardIdx+"/"+loginId);
		BoardDAO dao = new BoardDAO();
		CommentDTO commentUpdatedto = dao.commentUpdateForm(reIdx);
		dao = new BoardDAO();
		dao.upDown(boardIdx); //댓글수정할때도 조회수가 올라가버려서
		page = "/boardDetail?boardIdx="+boardIdx+"&page="+currPage;
		if(loginId.equals(id)) {//로그인아이디와 작성자 아이디가 같으면
			req.setAttribute("commentUpdatedto", commentUpdatedto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
	}

	public void commentUpdate() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String id = req.getParameter("id");
		String reIdx = req.getParameter("reIdx");
		String boardIdx = req.getParameter("boardIdx");
		String comment = req.getParameter("comment");
		String currPage = req.getParameter("page");
		System.out.println(id+"/"+reIdx+"/"+boardIdx+"/"+loginId);
		
		if(loginId.equals(id)) {
			BoardDAO dao = new BoardDAO();
			dao.upDown(boardIdx); //댓글수정할때도 조회수가 올라가버려서
			page="/boardDetail?boardIdx="+boardIdx+"&page="+currPage;
			msg="댓글 수정에 실패하였습니다.";
			dao = new BoardDAO();
			if(dao.commentUpdate(reIdx, comment)) {
				msg="댓글 수정이 완료되었습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}		
	}

	public void commentDel() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String isManager = (String) req.getSession().getAttribute("isManager");
		String id = req.getParameter("id");
		String reIdx = req.getParameter("reIdx");
		String boardIdx = req.getParameter("boardIdx");
		System.out.println(id+"/"+reIdx+"/"+boardIdx+"/"+loginId);
		page="/boardDetail?boardIdx="+boardIdx;
		BoardDAO dao = new BoardDAO();
		dao.upDown(boardIdx); //댓글삭제할때도 조회수가 올라가버려서
		if(loginId.equals(id) || isManager.equals("true")) {//본인이거나 관리자일때
			msg="댓글 삭제에 실패했습니다.";
			dao = new BoardDAO();
			if(dao.commentDel(reIdx)) {
				msg="댓글 삭제에 성공했습니다.";
			}
		}
		req.setAttribute("msg",msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void boardSearch() throws ServletException, IOException {
		String searchType = req.getParameter("searchType");
		String keyword = req.getParameter("boardkeyword");
		System.out.println(searchType+"/"+keyword);
		String pageParam =  req.getParameter("page");
		System.out.println("page:"+pageParam);
		int group =1;
		if(pageParam!=null) {
			group = Integer.parseInt(pageParam);
		}
		BoardDAO dao = new BoardDAO();
		String url = "searchType=" + searchType + "&boardkeyword=" + keyword;
		
		HashMap<String, Object> map = dao.boardSearch(group,searchType,keyword);
		dao = new BoardDAO();
		ArrayList<BoardDTO> managerbbsList = dao.managerbbsList();
		
		System.out.println(map.get("maxPage"));
		req.setAttribute("boardkeyword", keyword);
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("url", url);
		req.setAttribute("list",map.get("list"));
		req.setAttribute("managerbbsList", managerbbsList);
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("boardSearchList.jsp");
		dis.forward(req, resp);
	}


	public void boardReportForm() throws ServletException, IOException {
		String boardIdx = req.getParameter("boardIdx");
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println("신고할 게시글번호: "+boardIdx+"/"+loginId);
		if(loginId!=null) {
			req.setAttribute("boardIdx", boardIdx);
			dis = req.getRequestDispatcher("boardReportForm.jsp");
			dis.forward(req, resp);			
		}else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
	}
	
	public void boardReport() throws ServletException, IOException {
		String boardIdx = req.getParameter("boardIdx");
		String loginId = (String) req.getSession().getAttribute("loginId");
		String reason = req.getParameter("reason");
		System.out.println(boardIdx+"/"+loginId+"/"+reason);
		if(loginId!=null) {
			BoardDAO dao = new BoardDAO();
			
			msg= "이미 신고한 게시글입니다.";
			page="boardReportForm.jsp";
			if(dao.boardReport(boardIdx,loginId,reason)) {
				msg="신고처리가 완료되었습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);			
		}else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
	}

	public void commReportForm() throws ServletException, IOException {
		String reIdx = req.getParameter("reIdx");
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println("신고할 댓글번호: "+reIdx+"/"+loginId);
		if(loginId!=null) {
			req.setAttribute("reIdx", reIdx);
			dis = req.getRequestDispatcher("commReportForm.jsp");
			dis.forward(req, resp);			
		}else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
		
	}

	public void commReport() throws ServletException, IOException {
		String reIdx = req.getParameter("reIdx");
		String loginId = (String) req.getSession().getAttribute("loginId");
		String reason = req.getParameter("reason");
		System.out.println(reIdx+"/"+loginId+"/"+reason);
		if(loginId!=null) {
			BoardDAO dao = new BoardDAO();
			msg= "이미 신고한 댓글입니다.";
			page="commReportForm.jsp";
			if(dao.commReport(reIdx,loginId,reason)) {
				msg="신고처리가 완료되었습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);			
		}else {
			msg = "로그인이 필요한 서비스입니다.";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("index.jsp");
			dis.forward(req, resp);	
		}
		
	}

	public ArrayList<BoardDTO> mainBoardList() throws ServletException, IOException {
		
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardDTO> list = dao.mainBoardList();
		System.out.println("메인에서 가져오는 리스트  : "+list);
		return list;
		
//		req.getSession().setAttribute("list", list);
//		ArrayList<BoardDTO> test = (ArrayList<BoardDTO>) req.getSession().getAttribute("list");
//		 req.setAttribute("list", list);
//		 dis = req.getRequestDispatcher("mainBoardList.jsp");
//		 dis.forward(req, resp);
		 
	}
}
