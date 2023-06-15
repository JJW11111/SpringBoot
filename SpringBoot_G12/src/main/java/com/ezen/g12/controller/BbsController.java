package com.ezen.g12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g12.dao.IBbsDao;
import com.ezen.g12.dto.BbsDto;

@Controller
public class BbsController {
	
	@Autowired
	IBbsDao bdao;
	
	@RequestMapping("/")
	public ModelAndView root(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", bdao.getList());
		mav.setViewName("bbslist");
		return mav;
	}
	
	@RequestMapping("writeForm")
	public String writeForm() {
		return "writeForm";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BbsDto bbsdto) {
		bdao.write(bbsdto);
//		bdao.write(bbsdto.getWriter(), bbsdto.getTitle(), bbsdto.getContent());
		return "redirect:/";
	}

	
	@RequestMapping("/view")
	public ModelAndView view(@RequestParam("id") int id, Model model) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto", bdao.view(id));
		mav.setViewName("view");
		
		return mav;

	}
	@RequestMapping("/updateForm")
	public ModelAndView updateForm(@RequestParam("id") int id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", bdao.view(id));
		mav.setViewName("updateForm");
		return mav;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(BbsDto bbsdto) {
		
		bdao.update(bbsdto);
		return "redirect:/";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") int id) {
	    bdao.delete(id);
	    return "redirect:/";
	}
	
}
