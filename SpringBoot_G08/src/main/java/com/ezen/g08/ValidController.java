package com.ezen.g08;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class ValidController {
	@RequestMapping("/")
	public String root() throws Exception {
		return "startPage";
	}
	
	
	@RequestMapping("/create")
	public String create(@ModelAttribute("dto") @Valid ContentDto contentdto,BindingResult result, Model model) {
		
		String page = "DonePage";
		
	if(result.hasErrors()) {
		page="startPage";
		if(result.getFieldError("writer")!=null) {
//			model.addAttribute("message","writer가 비어있습니다");
			model.addAttribute("message",result.getFieldError("writer").getDefaultMessage());
		}else if (result.getFieldError("content")!=null){
//			model.addAttribute("message","content가 비어있습니다");	
			model.addAttribute("message",result.getFieldError("content").getDefaultMessage());
			}
	}

	return page;
	}
}

