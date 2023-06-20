package com.ezen.g15.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.g15.dto.ProductVO;
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
		
		return "index";
	}
}
