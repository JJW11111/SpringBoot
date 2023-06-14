package com.ezen.g10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/writeForm")
	public String writeForm() {
		
		return "writeForm";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
//	@PostMapping("/write") // 위에거랑 둘 중에 하나만 써도 됨
	public String write(BbsDto bbsdto) {
		bdao.write(bbsdto);
		return "redirect:/";
	}
	
	@RequestMapping("view")
	public ModelAndView view(@RequestParam("id") int id, Model model) {
		ModelAndView mav = new ModelAndView();
		
//		BbsDto bdto = bdao.view(id);
//		model.addAttribute(bdto);
		mav.addObject("dto", bdao.view(id));
		
		mav.setViewName("view");
		
		return mav;
	}
	
	@RequestMapping("/updateForm")
	public ModelAndView updateForm(@RequestParam("id") int id, Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", bdao.view(id));
		mav.setViewName("updateForm");
		return mav;
	}
	
	@PostMapping("/update")
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
