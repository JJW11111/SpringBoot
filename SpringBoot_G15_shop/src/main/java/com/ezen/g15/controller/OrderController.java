package com.ezen.g15.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.dto.OrderVO;
import com.ezen.g15.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService os;
	
	@RequestMapping("/orderInsert")
	public String orderInsert(HttpServletRequest request){
		
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		int oseq = 0;
		if(mvo == null) {
			return "member/login";
		}else if(mvo.getZip_num()==null || mvo.getAddress1() == null || mvo.getAddress2() == null ) {
			return "redirect:/memberEditForm";
		}else {
			HashMap<String, Object> result = os.insertOrder(mvo.getId());
			oseq = (Integer)result.get("oseq");	
		}
		return "redirect:orderList?oseq=" + oseq;
	}
	
	
	@RequestMapping("/orderList")
	public ModelAndView orderList(@RequestParam("oseq") int oseq,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if(mvo == null) {
			mav.addObject("member/login");
		}else {
			HashMap<String, Object> result = os.listOrderByOseq(oseq);
			mav.addObject("orderList", (List<OrderVO>)result.get("orderList"));
			mav.addObject("totalPrice", (Integer)result.get("totalPrice"));
			mav.setViewName("mypage/orderList");
		}
		return mav;
	}
}
