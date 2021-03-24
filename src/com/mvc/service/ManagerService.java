package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.dao.ManagerDAO;
import com.mvc.dao.TripDAO;
import com.mvc.dto.AreaDTO;
import com.mvc.dto.CityDTO;
import com.mvc.dto.ContentDTO;
import com.mvc.dto.LargeDTO;
import com.mvc.dto.ManagerDTO;
import com.mvc.dto.MediumDTO;
import com.mvc.dto.SmallDTO;
import com.mvc.dto.TripDTO;
import com.mvc.dto.TripDetailDTO;

public class ManagerService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;
	String page = "";
	String msg = "";

	public ManagerService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void managerList() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
		ManagerDAO dao = new ManagerDAO();
		ArrayList<ManagerDTO> managerList = dao.managerList();
		req.setAttribute("managerList", managerList);
		dis = req.getRequestDispatcher("managerList.jsp");
		dis.forward(req, resp);
		dao.resClose();
	}else {
		req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
		dis = req.getRequestDispatcher("login.jsp");
		dis.forward(req, resp);
	}
	}

	public void managerDel() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
		String managerid = req.getParameter("managerid");
		System.out.println("삭제할 관리자번호 : " + managerid);

		msg = "";
		page = "/managerList";

		ManagerDAO dao = new ManagerDAO();
		if (dao.managerDel(managerid)) {
			msg = "해당 관리자를 삭제하였습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		dao.resClose();
	} else {
		req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
		dis = req.getRequestDispatcher("login.jsp");
		dis.forward(req, resp);
	}
}

	public void managerRegist() throws ServletException, IOException {
		String isManager = (String) req.getSession().getAttribute("isManager");

		if (isManager=="true") {
		String managerId = req.getParameter("managerId");
		String managerPw = req.getParameter("managerPw");
		String managerName = req.getParameter("managerName");
		System.out.println(managerId + "/" + managerPw + "/" + managerName);

		ManagerDAO dao = new ManagerDAO();
		ManagerDTO dto = new ManagerDTO();

		dto.setManagerid(managerId);
		dto.setPw(managerPw);
		dto.setName(managerName);

		msg = "관리자 등록에 실패 하였습니다.";

		if (dao.managerRegist(dto)) {
			// page = "/managerList"; //페이지 닫기..?
			msg = "관리자 등록에 성공 하였습니다.";
			

		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher("managerRegistClose.jsp");
		dis.forward(req, resp);
		dao.resClose();

	}else {
		
		req.setAttribute("msg", "로그인 후 사용이 가능한 서비스 입니다.");
		dis = req.getRequestDispatcher("login.jsp");
		dis.forward(req, resp);
	}
	
	}

}