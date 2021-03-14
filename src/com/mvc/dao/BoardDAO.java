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
import com.mvc.dto.CommentDTO;

public class BoardDAO {
	
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public BoardDAO() {
		
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resClose() {
			try {
				if(conn!=null) {
				conn.close();
				}
				if(ps!=null) {
					ps.close();
				}
				if(rs!=null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public HashMap<String, Object> list(int page) {
		
		HashMap<String,Object> map= new HashMap<String, Object>();
		int pagePerCnt = 10;
		int end = page*pagePerCnt;
		int start = end-(pagePerCnt-1);
		String sql ="SELECT boardIdx,subject,bHit,reg_date,id FROM (" + 
				"    SELECT ROW_NUMBER() OVER(ORDER BY boardIdx DESC) AS rnum,boardIdx,subject,bHit,reg_date,id " + 
				"        FROM bbs WHERE DEACTIVATE='FALSE'" + 
				") WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBoardIdx(rs.getInt("boardIdx"));
				dto.setSubject(rs.getString("subject"));
				dto.setbHit(rs.getInt("bHit"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}
			BoardDTO dto = new BoardDTO();
			int maxPage= getMaxPage(pagePerCnt);
			map.put("list",list);
			map.put("maxPage",maxPage);
			System.out.println("maxPage: "+ maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}
	
	private int getMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(boardIdx) FROM bbs WHERE DEACTIVATE='FALSE'";
		int max=0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}
	
	
	public long write(BoardDTO dto) {
		String sql = "INSERT INTO bbs (boardIdx,subject,content,id)VALUES (bbs_seq.NEXTVAL,?,?,?)";
		long pk = 0;
		try {
			ps = conn.prepareStatement(sql,new String[] {"boardIdx"});
			ps.setString(1, dto.getSubject());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getId());
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				pk = rs.getLong(1);
				System.out.println("idx :"+pk);
				if(dto.getOriFileName()!=null) {
					sql = "INSERT INTO photo (fileIdx,oriFileName,newFileName,boardIdx)VALUES(photo_seq.NEXTVAL,?,?,?)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, dto.getOriFileName());
					ps.setString(2, dto.getNewFileName());
					ps.setLong(3, pk);
					ps.executeUpdate();					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return pk;
	}


	public BoardDTO detail(String boardIdx) {
		String sql="SELECT b.boardIdx, b.subject, b.content, b.bHit,b.id,b.deactivate, p.oriFileName, p.newFileName "+ 
				"FROM bbs b, photo p WHERE b.boardIdx = p.boardIdx(+) AND b.boardIdx = ?";		
		BoardDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(boardIdx));
			rs=ps.executeQuery();
			if(rs.next()) {
				dto = new BoardDTO();
				dto.setBoardIdx(rs.getInt("boardIdx"));
				dto.setId(rs.getString("id"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setbHit(rs.getInt("bHit"));
				dto.setDeactivate(rs.getString("deactivate"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return dto;
	}

	public void upHit(String boardIdx) {
		System.out.println("조회수올리기");
		String sql ="UPDATE bbs SET bHit= bHit+1 WHERE boardIdx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, boardIdx);
			int success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
	}
	
	public void upDown(String boardIdx) {
		System.out.println("조회수내리기");
		String sql ="UPDATE bbs SET bHit= bHit-1 WHERE boardIdx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, boardIdx);
			int success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
	}

	public int update(BoardDTO dto) {
		String sql = "UPDATE bbs SET subject=?,content=? WHERE boardIdx=?";
		int success = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getSubject());
			ps.setString(2, dto.getContent());
			ps.setInt(3, dto.getBoardIdx());
			success = ps.executeUpdate();
			System.out.println("업데이트 완료된 개수 : "+success);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}

	public String getFileName(String boardIdx) {
		String newFileName = null;
		String sql = "SELECT oriFileName,newFileName FROM photo WHERE boardIdx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, boardIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				newFileName = rs.getString("newFileName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return newFileName;
	}

	public int updateFileName(String delFileName, BoardDTO dto) {
		String sql = "";
		int success = 0;
		
		try {
			if(delFileName!=null) { //기존파일이 있고 사진을 변경할 시
				sql = "UPDATE photo SET newFileName=?,oriFileName=? WHERE boardIdx=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getNewFileName());
				ps.setString(2, dto.getOriFileName());
				ps.setInt(3, dto.getBoardIdx());	
			}else {//기존사진이 없고 사진을 신규로 올릴 시
				sql = "INSERT INTO photo (fileIdx,oriFileName,newFileName,boardIdx)VALUES(photo_seq.NEXTVAL,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getOriFileName());
				ps.setString(2, dto.getNewFileName());
				ps.setInt(3, dto.getBoardIdx());
			}
			success = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		System.out.println(sql+" 성공여부  :" +success);
		return success;	
		
	}

	public int del(String boardIdx, String newFileName) {
		int success = 0;
		
		try {
			if(newFileName!= null) {//사진이 있는경우 사진 먼저 비활성화
				String photoSQL = "UPDATE photo SET deactivate='TRUE' WHERE boardIdx = ?";
				ps = conn.prepareStatement(photoSQL);
				ps.setString(1, boardIdx);
				ps.executeUpdate();
			}
			String bbsSQL = "UPDATE bbs SET deactivate='TRUE' WHERE boardIdx = ?";
			ps = conn.prepareStatement(bbsSQL);
			ps.setString(1, boardIdx);
			success = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resClose();
		}	
		return success;
	}
   
	public boolean commentWrite(String boardIdx, String comment, String loginId) {
		String sql = "INSERT INTO bbs_comment (reIdx,content,id,boardIdx)VALUES(comment_seq.NEXTVAL,?,?,?)";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, comment);
			ps.setString(2, loginId);
			ps.setString(3, boardIdx);
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}

	public ArrayList<CommentDTO> comm_list(String boardIdx) {
		String sql = "SELECT reIdx,id,content,reg_date FROM BBS_COMMENT WHERE boardIdx=? AND deactivate='FALSE' ORDER BY reIdx DESC";
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, boardIdx);
			rs = ps.executeQuery();
			while(rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setId(rs.getString("id"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setReIdx(rs.getInt("reIdx"));
				list.add(dto);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return list;
	}


	public CommentDTO commentUpdateForm(String reIdx) {
		String sql = "SELECT content,id,reIdx FROM bbs_comment WHERE reIdx=?";
		CommentDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, reIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new CommentDTO();
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setReIdx(rs.getInt("reIdx"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return dto;
	}
	
	public boolean commentUpdate(String reIdx, String comment) {
		String sql = "UPDATE BBS_COMMENT SET content=? WHERE reIdx=?";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, comment);
			ps.setString(2, reIdx);
			if(ps.executeUpdate()>0) {
				success=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}

	public boolean commentDel(String reIdx) {
		String sql ="UPDATE BBS_COMMENT SET deactivate='TRUE' WHERE reIdx=?";
		boolean success=false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, reIdx);
			if(ps.executeUpdate()>0) {
				success=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	

}
