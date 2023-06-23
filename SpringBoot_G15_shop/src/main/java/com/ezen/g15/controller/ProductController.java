package com.ezen.g15.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g15.dto.ProductVO;
import com.ezen.g15.service.AdminService;
import com.ezen.g15.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService ps;
	
	
	@RequestMapping("/")
	public String main(Model model) {
		HashMap<String, Object> result = ps.getBestNewList();
		
		List<ProductVO> list = ( List<ProductVO> )result.get("newProductList");
		model.addAttribute("newProductList", list );
		
		model.addAttribute("bestProductList", (List<ProductVO>)result.get("bestProductList") );
		model.addAttribute("bannerList", ps.getBannerList() );
		model.addAttribute("size", ps.getBannerList().size());
		
		return "index";
	}
	
	
	
	@RequestMapping("/catagory")
	public ModelAndView catagory( @RequestParam("kind") String kind ) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("productKindList",  ps.getKindList( kind ) );
		mav.setViewName("product/productKind");
		return mav;
		
	}
	
	
	@RequestMapping("/productDetail")
	public ModelAndView product_detail(  @RequestParam("pseq") int pseq ) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("productVO" ,  ps.getProduct(pseq ) );
		mav.setViewName("product/productDetail");
		return mav;
	}
}














