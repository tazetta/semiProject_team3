package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.dao.ManagerDAO;
import com.mvc.dto.ManagerDTO;

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
		ManagerDAO dao = new ManagerDAO();
		ArrayList<ManagerDTO> managerList = dao.managerList();
		req.setAttribute("managerList", managerList);
		dis = req.getRequestDispatcher("./managerList");		
		dis.forward(req, resp);
	}

		public void managerDel() throws ServletException, IOException {
			String managerid = req.getParameter("managerid");
			System.out.println("삭제할 관리자번호 : "+managerid);
			
			msg = "";
			page ="/adminList";
			
			ManagerDAO dao = new ManagerDAO();
			if(dao.adminDel(managerid)) {	
				msg="팝업을 삭제하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);		
			dis.forward(req, resp);		
		}
}