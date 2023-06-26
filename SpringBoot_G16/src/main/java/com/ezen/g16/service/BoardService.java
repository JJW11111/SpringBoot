package com.ezen.g16.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g16.dao.IBoardDao;
import com.ezen.g16.dto.Paging;

@Service
public class BoardService {

	@Autowired
	IBoardDao bdao;
	
	public void selectBoard(HashMap<String, Object> paramMap) {
		
		HttpServletRequest request = (HttpServletRequest) paramMap.get("request");
		HttpSession session = request.getSession();
		
		int page = 1;
		if( request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", page);
		}else if(session.getAttribute("page")!=null) {
			page = (Integer)session.getAttribute("page");
		}else {
			session.removeAttribute("page");
		}
		
		Paging paging = new Paging();
		paging.setPage(page);
		
		// getAllCount 메서드를 이용한 총 게시물 갯수를 리턴
		// 필요한 건 게시물 갯수를 계산해서 나에게 담아 보내줄 OUT 변수가 피요함
		paramMap.put("cnt", 0);
		bdao.getAllCount(paramMap);
		// getAllCount가 실행되고 나면 "cnt" 키값에 해당하는 밸류가 총 게시물 갯수가 됨
		int count = (Integer)paramMap.get("cnt");
		
		paging.setTotalCount(count);
		paging.paging();
		
		paramMap.put("startNum", paging.getStartNum());
		paramMap.put("endNum", paging.getEndNum());
		
		bdao.selectBoard(paramMap); // 결과가 ref_cursor 에 담김
		
		paramMap.put("paging", paging);
		
	}

	public void getBoard(HashMap<String, Object> paramMap) {
		bdao.plusOneReadCount(paramMap);
		bdao.getBoard(paramMap);
		
	}

	public void insertReply(HashMap<String, Object> paramMap) {
		bdao.insertReply(paramMap);
		
	}

	public void deleteReply(HashMap<String, Object> paramMap) {
		bdao.deleteReply(paramMap);
		
	}

	public void getBoardWithoutCount(HashMap<String, Object> paramMap) {
		bdao.getBoard(paramMap);		
	}
	
	
	
}











