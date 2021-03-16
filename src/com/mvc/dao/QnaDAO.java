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
	
	/*고객센터 리스트*/
	public HashMap<String, Object> qnaList(String loginId) {
		String sql = " SELECT rnum, qnaidx, subject, reg_date, id "
				+ "FROM (SELECT  ROW_NUMBER() OVER(ORDER BY qnaidx DESC) AS rnum, qnaidx ,subject , reg_date,id FROM question)" + 
				" WHERE id=?";
		ArrayList<QnaDTO> list = new ArrayList<QnaDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setRnum(rs.getInt("rnum"));
				dto.setQnaIdx(rs.getInt("qnaidx"));
				dto.setSubject(rs.getString("subject"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
			map.put("list",list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}return map;
	}

}
