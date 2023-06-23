package com.ezen.g16.comtroller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g16.dto.Paging;
import com.ezen.g16.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	BoardService bs;
	
	@RequestMapping("/main")
	public ModelAndView goMain(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser")==null)
			mav.setViewName("loginform");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			paramMap.put("ref_cursor", null);
			
			bs.selectBoard(paramMap);
			
			ArrayList<HashMap<String, Object>> list 
					= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			mav.addObject("boardList", list);
			mav.addObject("paging", (Paging)paramMap.get("paging"));
			mav.setViewName("board/main");
			
			
		}
		return mav;
	}
			
	
}












