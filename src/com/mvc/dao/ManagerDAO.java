package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.dto.ManagerDTO;

public class ManagerDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public ManagerDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resClose() {
		try {
			if(rs != null) {	rs.close();}
			if(ps != null) {	ps.close();}
			if(conn != null) {	conn.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ManagerDTO> managerList() {
		
		ArrayList<ManagerDTO> managerList = new ArrayList<ManagerDTO>();
		String sql = "SELECT managerid, name, reg_date FROM manager WHERE managerid NOT IN ('sysadmin') ORDER BY reg_date DESC";
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				ManagerDTO dto = new ManagerDTO();
				dto.setManagerid(rs.getString("managerid"));
				dto.setName(rs.getString("name"));
				dto.setReg_date(rs.getDate("reg_date"));
				managerList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		return managerList;
	}

		public boolean managerDel(String managerid) {
			
			String sql="DELETE FROM manager WHERE managerid=?";
			boolean success = false;
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, managerid);
				if(ps.executeUpdate()>0) {
					success = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
					resClose();
			}	
			System.out.println("삭제여부 :"+success);
			return success;
	}

		public boolean managerRegist(ManagerDTO dto) {
		String sql = "INSERT INTO manager( managerid, pw, name) VALUES (?,?,?)";
		
		boolean success = false;	
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getManagerid());
			ps.setString(2, dto.getPw());
			ps.setString(3, dto.getName());
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		System.out.println("관리자 등록 성공여부:"+success);
		return success;
		}

}
