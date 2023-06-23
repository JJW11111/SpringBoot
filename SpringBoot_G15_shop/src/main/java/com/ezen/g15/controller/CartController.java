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

import com.ezen.g15.dto.CartVO;
import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.service.CartService;

@Controller
public class CartController {

	@Autowired
	CartService cs;
	
	@RequestMapping("/cartInsert")
	public String cartInsert(
			@RequestParam("pseq") int pseq , 
			@RequestParam("quantity") int quantity ,
			HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if( mvo == null) {
			return "member/login";
		}else {
			CartVO cvo = new CartVO();
			cvo.setId(mvo.getId());
			cvo.setPseq(pseq); 
			cvo.setQuantity(quantity);
			cs.insertCart(cvo);
		}
		return "redirect:/cartList";
	}
	
	
	@RequestMapping("cartList")
	public ModelAndView cart_list( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if( mvo == null) {
			mav.setViewName("member/login" );
		}else {
			HashMap<String , Object > result = cs.getCartList( mvo.getId() );
			mav.addObject( "cartList",  (List<CartVO>)result.get("cartList") );
			mav.addObject( "totalPrice",  (Integer)result.get("totalPrice" ) );
			mav.setViewName("mypage/cartList");
		}
		return mav;
	}
	
	
	@RequestMapping("cartDelete")
	public String cart_delete( @RequestParam("cseq") String[] cseqArr ) {
		
		for(String cseq : cseqArr) 
			cs.deleteCart(cseq);
		return "redirect:/cartList";
		
	}
}










