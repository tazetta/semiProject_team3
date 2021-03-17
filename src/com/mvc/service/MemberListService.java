package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.MemberListDAO;
import com.mvc.dto.MemberListDTO;

public class MemberListService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;
	String page = "";
	String msg = "";

	public MemberListService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void memberList() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {

			String pageParam = req.getParameter("page");
			System.out.println("page:" + pageParam);
			// 한페이지 그룹 -> 1~10번
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			MemberListDAO dao = new MemberListDAO();
			HashMap<String, Object> map = dao.memberList(group);

			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("memberList", map.get("memberList"));
			req.setAttribute("currPage", group);
			dis = req.getRequestDispatcher("memberList.jsp");
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDetail() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String id = req.getParameter("id");
			System.out.println("상세보기 id: " + id);

			String memberDetail = "/memberList";
			String page = memberDetail;

			MemberListDAO dao = new MemberListDAO();
			MemberListDTO dto = dao.memberDetail(id);

			if (dto != null) {
				dao = new MemberListDAO();
				page = "memberDetail.jsp";
				req.setAttribute("dto", dto);
			}
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDelList() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String pageParam = req.getParameter("page");
			System.out.println("page:" + pageParam);
			// 한페이지 그룹 -> 1~10번
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			MemberListDAO dao = new MemberListDAO();
			HashMap<String, Object> map = dao.memberDelList(group);

			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("memberDelList", map.get("memberDelList"));
			req.setAttribute("currPage", group);
			dis = req.getRequestDispatcher("memberDelList.jsp");
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDelDetail() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String id = req.getParameter("id");
			System.out.println("탈퇴회원 상세보기 id: " + id);

			String memberDelDetail = "/memberDetail";
			String page = memberDelDetail;

			MemberListDAO dao = new MemberListDAO();
			MemberListDTO dto = dao.memberDetail(id);

			if (dto != null) {
				dao = new MemberListDAO();
				page = "memberDelDetail.jsp";
				req.setAttribute("dto", dto);
			}
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberDraw() throws ServletException, IOException { // 탈퇴 회원 삭제

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String id = req.getParameter("id");
			System.out.println("삭제할 탈퇴회원 id: " + id);

			msg = "";
			page = "/memberDelList";

			MemberListDAO dao = new MemberListDAO();
			if (dao.memberDraw(id)) {
				msg = "해당 회원을 삭제하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberRestore() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String id = req.getParameter("id");
			System.out.println("복구할 탈퇴회원 id: " + id);

			msg = "";
			page = "/memberDelList";

			MemberListDAO dao = new MemberListDAO();
			if (dao.memberRestore(id)) {
				msg = "해당 회원을 복구하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberSearch() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		String searchType = req.getParameter("searchType");
		String memberKeyword = req.getParameter("memberKeyword");
		System.out.println("검색회원정보 : " + searchType + "/" + memberKeyword);
		if (loginId != null) {
			String pageParam = req.getParameter("page");
			System.out.println("page:" + pageParam);
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			MemberListDAO dao = new MemberListDAO();
			String url = "searchType=" + searchType + "&memberKeyword=" + memberKeyword;
			HashMap<String, Object> map = dao.memberSearch(group, searchType, memberKeyword);
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("memberSearchList", map.get("memberSearchList"));
			req.setAttribute("url", url);
			req.setAttribute("currPage", group);
			dis = req.getRequestDispatcher("memberSearchList.jsp");
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
		}
	}

	public void memberBlackAddForm() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String id = req.getParameter("id");
			String managerid = req.getParameter("managerid");
			System.out.println("블랙리스트에 추가할 id: " + id + "/" + managerid);

			MemberListDAO dao = new MemberListDAO();
			MemberListDTO dto = dao.memberDetail(id);

			page = "/memberBlackList";
			msg = "실패";

			if (dto != null) {
				page = "memberBlackAdd.jsp";
				req.setAttribute("dto", dto);
			}
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberBlackAdd() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String managerid = req.getParameter("managerid");
			String id = req.getParameter("id");
			String reason = req.getParameter("reason");
			System.out.println(managerid + "/" + id + "/" + reason);

			MemberListDAO dao = new MemberListDAO();
			MemberListDTO dto = new MemberListDTO();

			dto.setManagerid(managerid);
			dto.setId(id);
			dto.setReason(reason);

			page = "/memberBlackAdd.jsp";
			msg = "블랙리스트 등록 실패";

			if (dao.memberBlackAdd(dto)) {
				msg = "해당 회원을 블랙리스트에 추가하였습니다.";
				page = "/memberBlackList";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberBlackList() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {

			String pageParam = req.getParameter("page");
			System.out.println("page:" + pageParam);
			// 한페이지 그룹 -> 1~10번
			int group = 1;
			if (pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			MemberListDAO dao = new MemberListDAO();
			HashMap<String, Object> map = dao.memberBlackList(group);

			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("memberBlackList", map.get("memberBlackList"));
			req.setAttribute("currPage", group);
			dis = req.getRequestDispatcher("memberBlackList.jsp");
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberBlackDetail() throws ServletException, IOException {

		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String blackidx = req.getParameter("blackidx");
			System.out.println("상세보기 idx: " + blackidx);

			String memberBlackDetail = "/memberBlackList";
			String page = memberBlackDetail;

			MemberListDAO dao = new MemberListDAO();
			MemberListDTO dto = dao.memberBlackDetail(blackidx);

			if (dto != null) {
				dao = new MemberListDAO();
				page = "memberBlackDetail.jsp";
				req.setAttribute("dto", dto);
			}
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}

	public void memberBlackDel() throws ServletException, IOException { 
		
		String loginId = (String) req.getSession().getAttribute("loginId");

		if (loginId != null) {
			String blackidx = req.getParameter("blackidx");
			System.out.println("삭제할 블랙idx: " + blackidx);

			msg = "";
			page = "/memberBlackList";

			MemberListDAO dao = new MemberListDAO();
			if (dao.memberBlackDel(blackidx)) {
				msg = "블랙리스트에서 삭제하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
			dis = req.getRequestDispatcher("login.jsp");
			dis.forward(req, resp);
		}
	}
}