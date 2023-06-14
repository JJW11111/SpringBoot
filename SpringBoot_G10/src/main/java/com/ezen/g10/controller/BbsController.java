package com.ezen.g10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g10.dao.BbsDao;
import com.ezen.g10.dto.BbsDto;

@Controller
public class BbsController {
	
	@Autowired
	BbsDao bdao;
	
	@RequestMapping("/")
	public ModelAndView root() {
		ModelAndView mav = new ModelAndView();
		
//		List<BbsDto> list = bdao.getList();
//		mav.addObject("list", list);
		mav.addObject("list", bdao.getList());
		
		mav.setViewName("bbslist");
		return mav;
	}
}
