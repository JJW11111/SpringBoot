package com.ezen.g15.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.dto.QnaVO;
import com.ezen.g15.service.QnaService;

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
		
		HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    ModelAndView mav = new ModelAndView();
	    if (mvo == null) mav.setViewName("member/login");
	    else {
	    	mav.addObject("qnaList", qs.listQna() );
	    	mav.setViewName("qna/qnaList");
	    }
		return mav;
	}
	
	@RequestMapping("/qnaView")
	public ModelAndView qna_view( HttpServletRequest request, @RequestParam("qseq") int qseq) {
		HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    ModelAndView mav = new ModelAndView();
		if (mvo == null) mav.setViewName("member/login");
		else {
			mav.addObject("qnaVO", qs.getQna(qseq) );
			mav.setViewName("qna/qnaView");
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

		QnaVO qvo = qs.getQna(qseq);
		model.addAttribute("qseq" , qseq);
		
		if( qvo.getPass().equals(pass) ) {
			return "qna/checkPassSuccess";
		}else {
			model.addAttribute("message", "비밀번호가 맞지 않습니다");
			return "qna/checkPass";
		}
			
	}
	
	
	
	@RequestMapping("/qnaWriteForm")
	public String qna_writre_form(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
	    MemberVO mvo= (MemberVO) session.getAttribute("loginUser");    	    
	    
	    if (mvo == null) return "member/login";
	    return "qna/qnaWrite";
	}
	
	
	@RequestMapping(value="/qnaWrite", method=RequestMethod.POST)
	public ModelAndView qna_write(
						@ModelAttribute("dto") @Valid QnaVO qnavo,
						@RequestParam(value="pass", required=false) String pass,
						@RequestParam(value="check", required=false) String check,
						BindingResult result,  HttpServletRequest request	) {
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if( mvo == null ) {
			mav.setViewName("member/login");
		}else {
			
			if(result.getFieldError("subject") != null ) 
	    		mav.addObject("message", result.getFieldError("subject").getDefaultMessage() );
	    	else if(result.getFieldError("content") != null )
	    		mav.addObject("message", result.getFieldError("content").getDefaultMessage());
	    	else {
	    		if( check == null ) {
	    			qnavo.setPasscheck( "N" );
	    			qnavo.setPass("");
	    		}else {
	    			qnavo.setPasscheck( "Y" );
	    			qnavo.setPass(pass);
	    		}
	    		qnavo.setId( mvo.getId() );
	    		qs.insertQna( qnavo );
	    		mav.setViewName("redirect:/qnaList");
	    	}
		}
		return mav;
	}
			
}















