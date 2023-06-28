package com.ezen.g17.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g17.service.QnaService;

@Controller
public class QnaController {

	@Autowired
	QnaService qs;
	
	@RequestMapping("/customer")
	public String customer() {
		return "qna/intro";
	}
	
	@RequestMapping("qnaList")
	public ModelAndView qna_list( HttpServletRequest request) {
	    ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
		 = (HashMap<String, Object> )session.getAttribute("loginUser");
		if(loginUser == null) mav.setViewName("member/login");
	    else {

	    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
	    	paramMap.put("ref_cursor", null);
	    	qs.listQna(paramMap);
	    	ArrayList<HashMap<String, Object>> list 
	    	= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
	    	
	    	mav.addObject("qnaList", list);
	    	mav.setViewName("qna/qnaList");
	    }
		return mav;
	}
	
	@RequestMapping("/passCheck")
	public ModelAndView passCheck( @RequestParam("qseq") int qseq ) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("qseq", qseq);
		mav.setViewName("qna/checkPass");
		
		return mav;
	}
	
	@RequestMapping(value="/qnaCheckPass", method=RequestMethod.POST)
	public String qnaCheckPass( 
			@RequestParam("qseq") int qseq, 
			@RequestParam("pass") String pass, Model model ) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("qseq", qseq);
		paramMap.put("ref_cursor", null);
		qs.getQna(paramMap);
		
		ArrayList<HashMap<String, Object>> list
		= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
		HashMap<String, Object> qvo = list.get(0);
		
		if( qvo.get("PASS").equals(pass) ) {
			model.addAttribute("qseq", qseq);
			return "qna/checkPassSuccess";
		}else {
			model.addAttribute("message", "비밀번호가 맞지 않습니다");
			return "qna/checkPass";
		}
			
	}
	
	
	@RequestMapping("/qnaView")
	public ModelAndView qna_view( HttpServletRequest request, @RequestParam("qseq") int qseq) {
	    ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
		= (HashMap<String, Object>)session.getAttribute("loginUser");
		
		if (loginUser == null) 
			mav.setViewName("member/login");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("qseq", qseq);
			paramMap.put("ref_cursor", null);
			qs.getQna(paramMap);
			
			ArrayList<HashMap<String, Object>> list
			= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
			
			mav.addObject("qnaVO", list.get(0));
			mav.setViewName("qna/qnaView");
		}
		return mav;
	}
	
	
	@RequestMapping("/qnaWriteForm")
	public String qna_writre_form(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
		= (HashMap<String, Object>)session.getAttribute("loginUser");    	    
	    
	    if (loginUser == null) return "member/login";
	    return "qna/qnaWrite";
	}
	
	
	@RequestMapping("qnaWrite")
	public ModelAndView qna_write(HttpServletRequest request,
			@RequestParam(value="check", required=false) String check,
			@RequestParam(value="pass", required=false) String pass,
			@RequestParam("content") String content,
			@RequestParam("subject") String subject) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
		= (HashMap<String, Object>)session.getAttribute("loginUser");
	    if (loginUser == null) 
	    	mav.setViewName("member/login");
	    else {
	    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
	    	String id = (String)loginUser.get("ID");
	    	paramMap.put("id", id);
	    	if(check==null) {
		    	paramMap.put("check", "N");
		    	paramMap.put("pass", "");
	    	}else {
	    		paramMap.put("check", "Y");
		    	paramMap.put("pass", pass);
	    	}
	    	paramMap.put("subject", subject);
	    	paramMap.put("content", content);
	    	qs.insertQna(paramMap);
	    	mav.setViewName("redirect:/qnaList");
	    }
	    return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}















