package com.ezen.g17.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.dao.IAdminDao;
import com.ezen.g17.dto.Paging;

@Service
public class AdminService {

	@Autowired
	IAdminDao adao;

	public void getAdmin(HashMap<String, Object> paramMap) {
		adao.getAdmin( paramMap );		
	}

	public void getProductList(HashMap<String, Object> paramMap) {
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get("request");
		HttpSession session = request.getSession();
		
		int page=1;
		if( request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", page);
		}else if( session.getAttribute("page") != null ) {
			page = (Integer)session.getAttribute("page");
		}else {
			session.removeAttribute("page");
		}
		
		String key = "";
		if( request.getParameter("key") != null ) {
			key = request.getParameter("key");
			session.setAttribute("key", key);
		} else if( session.getAttribute("key")!= null ) {
			key = (String)session.getAttribute("key");
		} else {
			session.removeAttribute("key");
		}
		
		Paging paging = new Paging();
		paging.setPage(page);
		HashMap<String, Object> cntMap = new HashMap<String, Object>();
		cntMap.put("cnt", 0);  // OUT 변수용
		cntMap.put("tableName" , 1 );
		cntMap.put("key", key);
		adao.adminGetAllCount(cntMap);
		
		int count = Integer.parseInt( cntMap.get("cnt").toString() );
		paging.setTotalCount(count);
		paging.paging();
		
		paramMap.put("startNum", paging.getStartNum());
		paramMap.put("endNum", paging.getEndNum());
		paramMap.put("key", key);
		
		adao.getProductList(paramMap);
		paramMap.put("paging", paging);
	}

	public void insertProduct(HashMap<String, Object> paramMap) {
		adao.insertProduct( paramMap );
		
	}

	
}


















