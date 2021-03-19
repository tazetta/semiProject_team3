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
import com.mvc.dto.QnaDTO;

public class QnaDAO {
	Connection conn = null; 
	PreparedStatement ps = null; 
	ResultSet rs = null; 
	public QnaDAO() {
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

	/*고객센터 글쓰기*/
	public boolean writeQNA(QnaDTO dto) {
		String sql ="INSERT INTO question (qnaidx,subject,content,reg_date,id) VALUES(seq_question.NEXTVAL,?,?,SYSDATE,?)";
		boolean success =false;
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1, dto.getSubject());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getId());
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
	
	/*고객센터 글쓰기(관리자)*/
	public boolean writeAnswer(QnaDTO dto) {
		String sql ="INSERT INTO answer (ansidx, subjectA, contentA, reg_dateA, managerid,qnaidx) VALUES(seq_answer.NEXTVAL,?,?,SYSDATE,?,?)";
		boolean success =false;
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1, dto.getSubjectA());
			ps.setString(2, dto.getContentA());
			ps.setString(3, dto.getManagerid());
			ps.setInt(4, dto.getQnaIdx());
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
	
	
	/*고객센터 리스트(admin)*/
	public HashMap<String, Object> qnaList(String loginId, int group) {
		
		int pagePerCnt = 10; // 페이지 당 보여줄 갯수
		
		int end= group*pagePerCnt; //페이지 끝 rnum
		int start = end-(pagePerCnt-1); //페이지 시작 rnum
		
		String sql = "SELECT rnum, qnaidx, subject, reg_date, id, ansidx " + 
				"FROM (SELECT  ROW_NUMBER() OVER(ORDER BY q.qnaidx DESC) AS rnum, q.qnaidx , q.subject , q.reg_date, q.id, a.ansidx " + 
				"FROM question q,answer a WHERE  q.qnaidx=a.qnaidx(+)) WHERE rnum BETWEEN ? AND ?";
		ArrayList<QnaDTO> list = new ArrayList<QnaDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setRnum(rs.getInt("rnum"));
				dto.setQnaIdx(rs.getInt("qnaidx"));
				dto.setSubject(rs.getString("subject"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setAnsIdx(rs.getInt("ansidx"));
				list.add(dto);
			}
			int maxPage = getMaxPage(pagePerCnt); 
			System.out.println("maxPage:"+maxPage);

			map.put("list",list);
			map.put("maxPage", maxPage); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}return map;
	}
	
	/*고객센터 리스트(사용자)*/
	public HashMap<String, Object> qnaListUser(String loginId, int group) {
int pagePerCnt = 10; // 페이지 당 보여줄 갯수
		
		int end= group*pagePerCnt; //페이지 끝 rnum
		int start = end-(pagePerCnt-1); //페이지 시작 rnum
		
		String sql = "SELECT rnum, qnaidx, subject, reg_date, id, ansidx " + 
				"FROM (SELECT  ROW_NUMBER() OVER(ORDER BY q.qnaidx DESC) AS rnum, q.qnaidx , q.subject , q.reg_date, q.id, a.ansidx " + 
				"FROM question q,answer a WHERE q.id=? AND q.qnaidx=a.qnaidx(+)) WHERE rnum BETWEEN ? AND ?";
		ArrayList<QnaDTO> list = new ArrayList<QnaDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setRnum(rs.getInt("rnum"));
				dto.setQnaIdx(rs.getInt("qnaidx"));
				dto.setSubject(rs.getString("subject"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setAnsIdx(rs.getInt("ansIdx"));
				list.add(dto);
			}
			int maxPage = getMaxPageUser(pagePerCnt,loginId); 
			System.out.println("maxPage:"+maxPage);

			map.put("list",list);
			map.put("maxPage", maxPage); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}return map;
	}
	
	
	/* 마지막 페이지*/
	private int getMaxPage(int pagePerCnt) {
		 String sql= "SELECT COUNT(qnaidx) FROM question";
		 int max = 0;
		 try {
				ps = conn.prepareStatement(sql);
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
	
	/* 마지막 페이지(사용자)*/
	private int getMaxPageUser(int pagePerCnt, String loginId) {
		 String sql= "SELECT COUNT(qnaidx) FROM question WHERE id=?";
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
	
	
	/*상세보기(Q&A)*/
	public QnaDTO qnaDetail(String loginId, String qnaIdx) {
		String sql ="SELECT q.qnaidx, q.subject, q.id, q.content, q.reg_date, a.ansidx, a.managerid, a.subjecta, a.contenta "
				+ "FROM question q, answer a  WHERE q.qnaidx=? AND q.qnaidx=a.qnaidx(+)";
		QnaDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(qnaIdx));
			rs = ps.executeQuery();
			if(rs.next()) {
				 dto =new QnaDTO();
				dto.setQnaIdx(rs.getInt("qnaidx"));
				dto.setSubject(rs.getString("subject"));
				dto.setId(rs.getString("id"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
				//
				dto.setAnsIdx(rs.getInt("ansIdx"));
				dto.setManagerid(rs.getString("managerid"));
				dto.setSubjectA(rs.getString("subjecta"));
				dto.setContentA(rs.getString("contenta"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();}
		return dto;
	}
	
	/*게시글 삭제*/
	public boolean qnaDel(String qnaIdx) {
		String sql ="DELETE FROM question WHERE qnaidx=?";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(qnaIdx));
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
	
	/*미답변 리스트*/
	public HashMap<String, Object> unAnsList(int group) {
int pagePerCnt = 10; // 페이지 당 보여줄 갯수
		
		int end= group*pagePerCnt; //페이지 끝 rnum
		int start = end-(pagePerCnt-1); //페이지 시작 rnum
		
		String sql = "SELECT rnum, qnaidx, subject, reg_date, id, ansidx " + 
				"FROM (SELECT  ROW_NUMBER() OVER(ORDER BY q.qnaidx DESC ) AS rnum, q.qnaidx , q.subject , q.reg_date, q.id, a.ansidx " + 
				"FROM question q,answer a WHERE  q.qnaidx=a.qnaidx(+) AND a.ansidx is null) WHERE rnum BETWEEN ? AND ?";
		ArrayList<QnaDTO> list = new ArrayList<QnaDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setRnum(rs.getInt("rnum"));
				dto.setQnaIdx(rs.getInt("qnaidx"));
				dto.setSubject(rs.getString("subject"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				dto.setAnsIdx(rs.getInt("ansidx"));
				list.add(dto);
			}
			int maxPage = getMaxPageUn(pagePerCnt); 
			System.out.println("maxPage:"+maxPage);

			map.put("list",list);
			map.put("maxPage", maxPage); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}return map;
	}
	
	/*미답변 마지막 페이지*/
	/* 마지막 페이지*/
	private int getMaxPageUn(int pagePerCnt) {
		 String sql= "SELECT COUNT(q.qnaidx) FROM question q, answer a WHERE  q.qnaidx=a.qnaidx(+)  AND   ansidx is null";
		 int max = 0;
		 try {
				ps = conn.prepareStatement(sql);
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
	
	

}
