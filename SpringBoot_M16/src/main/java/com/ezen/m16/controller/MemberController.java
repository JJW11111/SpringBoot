package com.ezen.m16.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

	@RequestMapping("/")
	public String root() {
		return "member/loginForm";
	}
	
	
	
}

