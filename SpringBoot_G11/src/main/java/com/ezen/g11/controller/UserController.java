package com.ezen.g11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g11.dao.IUserDao;
import com.ezen.g11.dto.UserDto;

@Controller 
public class UserController {
	
	@Autowired
	IUserDao udao;
	
	@RequestMapping("/")
	public ModelAndView root(Model model) {
		
		ModelAndView mav = new ModelAndView();
		List<UserDto> list = udao.getList();
		mav.addObject("users", list);
		mav.setViewName("userlist");
		return mav;
	}
}
