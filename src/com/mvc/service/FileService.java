package com.mvc.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

import com.mvc.dto.BoardDTO;

public class FileService {
	
	HttpServletRequest req = null;

	public FileService(HttpServletRequest req) {
		this.req = req;
	}

	public BoardDTO regist() {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String savePath="C:/upload/";//1. 저장할 폴더 지정
		int maxSize = 10*1024*1024;//2. 사이즈 지정
		BoardDTO dto = new BoardDTO();
		try {
			//3.파일저장
			MultipartRequest multi = new MultipartRequest(req,savePath,maxSize,"UTF-8");
			String boardIdx = multi.getParameter("boardIdx");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			String id = multi.getParameter("userId");
			System.out.println("글등록: "+boardIdx+"/"+subject+"/"+content+"/"+id);
			
			
			if(boardIdx!=null) {
				dto.setBoardIdx(Integer.parseInt(boardIdx));
			}
			if(subject.equals("")) {
				dto.setSubject("제목을 입력하세요");
			}else {
				dto.setSubject(subject);				
			}
			if(content.equals("")) {
				dto.setContent("내용을 입력하세요");
			}else {
				dto.setContent(content);				
			}
			dto.setId(id);
			
			String oriFileName = multi.getFilesystemName("photo");//4. 원본파일명 추출
			
			if(oriFileName!=null) {//파일이 있을때만
				String ext = oriFileName.substring(oriFileName.lastIndexOf("."));//확장자분리
				String newFileName =  System.currentTimeMillis()+ext;//5. 새파일명 만들기
				//6. 이름 변경
				File oriFile = new File(savePath+oriFileName);
				File newFile = new File(savePath+newFileName);
				oriFile.renameTo(newFile);
				dto.setOriFileName(oriFileName);
				dto.setNewFileName(newFileName);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	public void delete(String newFileName) {
		File file = new File("C:/upload/"+newFileName);
		
		if(file.exists()) {
			boolean success = file.delete();
			System.out.println("삭제 성공 여부 : "+ success);
		}
	}

}
