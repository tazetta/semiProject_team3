package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.BoardDTO;
import com.mvc.dto.MemberDTO;
import com.mvc.dto.TripDTO;


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

	
	/*로그인 +탈퇴여부확인*/
	public boolean login(String id, String pw) {
		
		boolean success = false;
		try {
			String sql = "SELECT id FROM member WHERE id=? AND pw=? AND withdraw='FALSE'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			
//			ResultSet rs2 = ps.executeQuery();
			
//			success = rs.next();
//			success = rs2.next();
			if(rs.next()) {
				success=true;
			}else {
				sql = "SELECT managerId FROM manager WHERE managerId=? AND pw=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, pw);
				rs = ps.executeQuery();
				if(rs.next()) {
					success = true;
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	
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

	/*내가 쓴 글 리스트 기능 +페이징*/
	public HashMap<String, Object> wroteList(String loginId, int page) {
	
		int pagePerCnt = 10; // 페이지 당 보여줄 갯수
		
		int end= page*pagePerCnt; //페이지 끝 rnum
		int start = end-(pagePerCnt-1); //페이지 시작 rnum

		String sql ="SELECT rnum, boardIdx,subject,bHit,reg_date,id "
				+ "FROM ( SELECT ROW_NUMBER() OVER(ORDER BY boardIdx DESC) AS rnum,boardIdx,subject,bHit,reg_date,id "
				+ "FROM bbs WHERE DEACTIVATE='FALSE') WHERE rnum BETWEEN ? AND ? AND id=?";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ps.setString(3, loginId);
			rs = ps.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setRnum(rs.getInt("rnum"));
				dto.setBoardIdx(rs.getInt("boardIdx"));
				dto.setSubject(rs.getString("subject"));
				dto.setbHit(rs.getInt("bHit"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
			
			int maxPage = getMaxPage(pagePerCnt,loginId); 
			System.out.println("maxPage:"+maxPage);
			map.put("list", list); 
			map.put("maxPage", maxPage); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();}
		return map;
	}
	
	/*마지막 페이지*/
	private int getMaxPage(int pagePerCnt, String loginId) {
		String sql =  "SELECT COUNT(boardIdx) FROM bbs WHERE DEACTIVATE='FALSE' AND id=?";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1); 
				max = (int)Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}
	
	/*중복체크*/
	public boolean overlay(String id) throws SQLException {
		
		boolean success = false;
		String sql = "SELECT id FROM member WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();
		success = rs.next();
		
		return !success;
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
	
	/*회원가입*/
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
	
	/*아이디찾기*/
	public String findId(String name, String phone) {
		
		String sql = "SELECT id FROM member WHERE name=? AND phone=?";
		String id = "";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, phone);
			rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		System.out.println(id);
		return id;
	}

	/*비밀번호 찾기*/
	public boolean findPw(String id, String name, String phone) {
		
		String sql = "SELECT pw FROM member WHERE id=? AND name=? AND phone=?";
		boolean pw = false;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, phone);
			rs = ps.executeQuery();
			if(rs.next()) {
				pw = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(pw);
		return pw;
	}

	/*회원탈퇴*/
	public boolean memberWithdraw(String loginId, String pw) {
		 String sql="UPDATE member SET withdraw='TRUE', update_date=SYSDATE WHERE id=? AND pw=? ";
		 boolean success= false;
		 try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, pw);
			if(ps.executeUpdate()>0) {
				success= true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}return success;

	}
	
	/*가봤어요 / 즐겨찾기 리스트*/
	public HashMap<String, Object> visitedList(String loginId, int group, int type) {
		int pagePerCnt = 4; // 페이지 당 보여줄 갯수
		
		int end= group*pagePerCnt; //페이지 끝 rnum
		int start = end-(pagePerCnt-1); //페이지 시작 rnum
		
		String sql ="SELECT title, firstimage, overview, reg_date  FROM (SELECT ROW_NUMBER() OVER(ORDER BY b.myidx DESC) as rnum, t.title, b.id, b.reg_date, t.firstimage, t.overview " + 
				"FROM bookmark b JOIN trip t USING (contentid) WHERE b.deactivate='FALSE'AND b.id=? AND b.type=?) WHERE rnum BETWEEN ? AND ? ";
		ArrayList<TripDTO> list = new ArrayList<TripDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, type);
			ps.setInt(3, start);
			ps.setInt(4, end);
			rs= ps.executeQuery();
			while(rs.next()) {
				TripDTO dto = new TripDTO();
				dto.setTitle(rs.getString("title"));
				dto.setFirstImage(rs.getString("firstimage"));
				dto.setOverview(rs.getString("overview"));
				dto.setReg_date(rs.getDate("reg_date"));
				list.add(dto);
			}
				int maxPage = getVisitedMaxPage(pagePerCnt,loginId); 
				System.out.println("maxPage:"+maxPage);
				map.put("list", list); 
				map.put("maxPage", maxPage); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return map;
	}
	
	/*가봤어요 마지막 페이지*/
	private int getVisitedMaxPage(int pagePerCnt, String loginId) {
		String sql =  "SELECT COUNT(myidx) FROM bookmark WHERE deactivate='FALSE' AND id=? AND type='2'";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1); 
				max = (int)Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	

	public boolean chkManager(String loginId) {
		boolean success = false;
		String sql = "SELECT managerId FROM manager WHERE managerId=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			success = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}

	/*비밀번호 찾기 후 수정*/
	public boolean findpwUpdate(String newPw) {
		
		String sql = "UPDATE member SET pw WHERE pw=?";
		boolean success = false;
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, newPw);
			if(ps.executeUpdate()>0) {
				success=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		
		return success;
	}
	

}
