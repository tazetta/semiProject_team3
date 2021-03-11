package com.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mvc.dao.MemberDAO;
import com.mvc.dto.BoardDTO;
import com.mvc.dto.MemberDTO;
import com.google.gson.Gson;

public class MemberService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;

	MemberDAO dao = null;

	String msg = "";
	String page = "";

	RequestDispatcher dis = null;

	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		dao = new MemberDAO();
	}

	/*로그인*/
	public void login() throws ServletException, IOException {
		// index.jsp에서 보내는 param값 받기
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		System.out.println(id + "/" + pw);
		boolean success = dao.login(id, pw); // dao에 id,pw 담아 보낸 후 로그인 메서드 실행
		System.out.println(success);
		if (success) { // 로그인 성공시 (true 반환시)
			page = "/profile"; // 지금은 list컨트롤러 없어서 404에러 떨어짐
	
			req.getSession().setAttribute("loginId", id); // "loginId"라는 이름으로 session에 저장
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	/* 회원정보 보기 */
	public void profile() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId + "의 프로필");
		if (loginId != null) {
			MemberDTO dto = dao.profile(loginId);
			System.out.println("dto:" + dto);
			if (dto != null) {
				page = "profile.jsp";
				req.setAttribute("profile", dto);
			}
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}
	
	/*수정할 회원정보 보기*/
	public void updateDetail() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId+"의 수정할 프로필");
		if(loginId != null) {
			MemberDTO dto = dao.profile(loginId);
			System.out.println("dto:"+dto);
			if(dto!=null) {
				page="updateForm.jsp";
				req.setAttribute("profile", dto);
			}
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else {
			resp.sendRedirect("index.jsp");
		}	
	}

	/* 회원정보 수정 */
	public void update() throws IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		System.out.println(loginId + "/" + pw + "/" + name + "/" + phone + "/" + email);

		MemberDTO dto = new MemberDTO();
		dto.setId(loginId);
		dto.setPw(pw);
		dto.setName(name);
		dto.setPhone(phone);
		dto.setEmail(email);

		boolean success = false;
		success = dao.update(dto);
		System.out.println("회원정보수정:" + success);

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("success", success);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		resp.getWriter().print(json);

	}

	/* 비밀번호 변경 */

	public void pwUpdate() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");

		String pw = req.getParameter("userPw");
		String updatePw = req.getParameter("updatePw");
		System.out.println(loginId + ":" + pw + "/" + updatePw);
		
		if (loginId != null) {

			boolean success = false;
			msg = "비밀번호를 확인해주세요";
			page = "pwUpdate.jsp";

			success = dao.updatePw(loginId, pw, updatePw);
			System.out.println("비밀번호수정:" + success);
			if (success) {
				msg = "비밀번호가 변경 되었습니다";
				page = "profile";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);

		}
	}

	/*내가 쓴 글 목록*/
	public void wroteList() {
		String loginId = (String) req.getSession().getAttribute("loginId");
		System.out.println(loginId+"의 게시글 목록");
		
		ArrayList<BoardDTO> list = dao.wroteList(loginId);
		
	}

	public void overlay() throws IOException {
		String id = req.getParameter("id");
		boolean success = false;
		System.out.println("id : "+id);
		MemberDAO dao = new MemberDAO();//MemberDAO를 객체화
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			success = dao.overlay(id);
			System.out.println("아이디 사용여부 : "+success);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//여기서 자원을 반납하면, service 내에서 DAO 를 사용 할 만큼 사용하고 닫아줄 수 있다.
			dao.resClose();
			map.put("use", success);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			//resp.setContentType("text/html charset-UTF-8");//한글이 없으면 굳이 안해줘도된다.
			//jsp와 Controller 가 같은 서버에 존재 할 때는 아래 구문이 불필요하다.
			//resp.setHeader("Access-Control-Allow-origin, "*");
			resp.getWriter().print(json);//페이지에 그려주는것.
		}
		
	}
	
	public void join() throws IOException {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String pw = req.getParameter("pw");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		System.out.println(id+"/"+name+"/"+pw+"/"+phone+"/"+email);
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setPw(pw);
		dto.setPhone(phone);
		dto.setEmail(email);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String msg = "회원정보를 확인해주세요";
		int success = 0;
		if(dao.join(dto)>0) {
			msg = "회원가입이 완료되었습니다.";
			success = 1;
		}
		map.put("msg", msg);
		map.put("success", success);
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8"); //보낼 데이터 타입과 한글깨짐 방지를 위한 인코딩 타입 지정
		resp.setHeader("Access-Control-Allow", "*");//javascript 에서 다른 도메인으로 통신은 기본적으로 안된다.(cross domain issue) - 허용 처리
		resp.getWriter().print(json);//페이지에 그려주는것
		
	}

	//컨트롤러로부터 로그인 요청시 실행
//	public boolean login() {
//		
//		MemberDAO dao = new MemberDAO();//dao 객체화
//		//유저아이디와 비밀번호를 가져옴
//		String id = req.getParameter("userId");
//		String pw = req.getParameter("userPw");
//		System.out.println(id+"/"+pw);//값이 제대로 들어왔는지
//		return dao.login(id, pw);//dao한테 받은 결과를 다시 컨트롤러에 전달
//	}
//	
//	public ArrayList<MemberDTO> main() {
//		
//		MemberDAO dao = new MemberDAO();
//		ArrayList<MemberDTO> list = dao.list();
//		return list;
//	}


	

}
