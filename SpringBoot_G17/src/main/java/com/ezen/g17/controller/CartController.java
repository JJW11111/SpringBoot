package com.ezen.g17.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g17.service.CartService;

@Controller
public class CartController {

	@Autowired
	CartService cs;
	
	@RequestMapping(value="/cartInsert")
	public String cartInsert( HttpServletRequest request, Model model,
			@RequestParam("pseq") int pseq,
			@RequestParam("quantity") int quantity ) {
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser 
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if( loginUser == null ) {
			return "member/login";
		}else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", loginUser.get("ID") );
			paramMap.put("pseq", pseq);
			paramMap.put("quantity" , quantity);
			cs.insertCart( paramMap );
		}
		return "redirect:/cartList";
	}

	
	@RequestMapping(value="/cartList")
	public ModelAndView cartList( HttpServletRequest request  ) {	
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser 
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if( loginUser == null ) {
			mav.setViewName("member/login");
		}else {
			HashMap<String , Object> paramMap = new HashMap<String , Object>();
			paramMap.put("id", loginUser.get("ID") );
			paramMap.put("ref_cursor", null);
			cs.listCart( paramMap );
			ArrayList<HashMap<String, Object>> list
				=(ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
			mav.addObject("cartList", list);
			mav.addObject("totalPrice", (Integer) paramMap.get("totalPrice") );
			mav.setViewName("mypage/cartList");
		}
		return mav;
	}
}










