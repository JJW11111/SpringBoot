package com.ezen.g15.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ezen.g15.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	AdminService as;
}
