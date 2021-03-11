package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.BoardDTO;
import com.mvc.dto.MemberDTO;


public class MemberDAO {
	Connection conn = null; // DB연결시 사용될 변수
	PreparedStatement ps = null; // DB재사용시 사용될 변수
	ResultSet rs = null; // SELECT문 실행시 사용될 변수

	/* DB연결 메서드 */
	public MemberDAO() { // 클래스 객체화시 호출되는 생성자
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 자원닫기 메서드 */
	public void resClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
/*로그인*/
//	public boolean login(String id, String pw) {
//		boolean success = false ; //결과값 담을 변수
//		String sql = "SELECT id FROM member WHERE id =? AND pw=?"; //쿼리문 준비 - SELECT
//		try {
//			ps = conn.prepareStatement(sql); //conn으로부터 ps가져와서 sql담기
//			ps.setString(1, id);
//			ps.setString(2, pw);
//			rs = ps.executeQuery(); //쿼리 실행
//			success = rs.next(); //다음값이 있으면 true, 없으면 false 담김
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally { //항상 자원 반납
//			resClose();
//		}
//		System.out.println("로그인 성공 여부:"+success);
//		return success;
//	}
	/*회원정보*/
	public MemberDTO profile(String loginId) {
		String sql = "SELECT name, phone, email FROM member WHERE id=?";
		MemberDTO dto =null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs= ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setEmail(rs.getString("email"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}return dto;
	}
	
	/*회원정보 수정*/
	public boolean update(MemberDTO dto) {
		boolean success= false;

		String sql = "UPDATE  member SET name=?, phone=?, email=?  WHERE id=? AND pw=?";
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getPhone());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getId());
			ps.setString(5, dto.getPw());
			if(ps.executeUpdate()>0) {
				success= true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	/*비밀번호 변경*/
	public boolean updatePw(String loginId, String pw, String updatePw) {
		String sql="UPDATE member SET pw=? WHERE id=? AND pw=?";
		boolean success = false;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, updatePw);
			ps.setString(2, loginId);
			ps.setString(3, pw);
			if(ps.executeUpdate()>0) {
				success= true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			resClose();
		}return success;
	
	}

	/*내가 쓴 글 리스트 기능*/
	public ArrayList<BoardDTO> wroteList(String loginId) {
		String sql ="SELECT boardidx, subject, reg_date FROM bbs WHERE id=?";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();}
		return list;
	}
	
	public boolean overlay(String id) throws SQLException {
		
		boolean success = false;
		String sql = "SELECT id FROM member WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();
		success = rs.next();
		
		return !success;
	}

	public boolean login(String id, String pw) {
		
		boolean success = false;
		String sql = "SELECT id FROM member WHERE id=? AND pw=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			success = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	public ArrayList<MemberDTO> list() {
		//쿼리 준비
		String sql = "SELECT id,name,email FROM member";
		//list에 DTO의 값들을 담기위해 ArrayList<MemberDTO>사용
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		try {
			ps = conn.prepareStatement(sql);//PreparedStatement 준비(Connection으로부터)
			rs = ps.executeQuery();//쿼리실행(SELECT문이므로 Query사용)
			while(rs.next()) {//순서대로 파라메터값 가져오기(1행씩 넘겨주다 더이상 행이 없으면 while문이 끝난다)
				MemberDTO dto = new MemberDTO();//dto 객체화(DB 값 담는다)
				//dto에 불러온 값 담아주기
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				list.add(dto);//list에 dto를 담는다
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();//자원닫기
		}
		return list;//list 반환
	}

	public int join(MemberDTO dto) {
		
		String sql="INSERT INTO member(id,name,pw,phone,email)VALUES(?,?,?,?,?)";
		int success = -1;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getPw());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getEmail());
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}	
		return success;
	}

	

}
