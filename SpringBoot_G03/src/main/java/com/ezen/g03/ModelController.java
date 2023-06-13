package com.ezen.g03;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModelController {
	
	@RequestMapping("/") // 요청주소가 http://localhost:8070/ 일 때 실행되는 메서드
	public @ResponseBody String root() {
		return "<h1>Model & View</h1>"; // 화면에 보여질 내용이 String 으로 리턴됨
	}
	
	@RequestMapping("/test1") // http://localhost:8070/test1 요청
	public String test1(HttpServletRequest request, Model model) {
		// RequestMapping 메서드의 매개변수로 HttpServletRequest 를 지정하면, Spring 전달해준 request
		// request 사용이 가능함
		request.setAttribute("name1", "홍길동");
		
		// 리턴받는 jsp 파일까지만 해당 내용을 전달 할 수 있는 1회용 자료 전달 도구
		model.addAttribute("name2", "김하나");
		
		return "test1"; // 화면에 보여질 jsp 파일 이름이 리턴됨
		// return 동작이나 이전에 별도의 request 전송 명령이 없어서 jsp 파일에 request 가 전달됨
	}
	
	
	@RequestMapping("/test2") // http://localhost:8070/test2 요청
	public String test2(HttpServletRequest request, Model model) {
		
//		model.addAttribute("name1", "홍길동"); // 다른 리퀘스트로 전달X
//		request.setAttribute("name2", "김하나"); // 다른 리퀘스트로 전달X
//		request.setAttribute("model", model); // 다른 리퀘스트로 전달X
		
		name4 = "홍길동";
		name5 = "김하나";
		
		// jsp 가 아닌 다른 request 요청으로 이동해야 할 때 아래와 같이 리턴함
		return "redirect:/test3";
	}
	
	String name4;
	String name5;
	
	@RequestMapping("/test3")
	public String test3(HttpServletRequest request, Model model) {
		
//		String name1 = (String)model.getAttribute("name1");
//		String name2 = (String)request.getAttribute("name2");	
//		model.addAttribute("name1", name1);
//		request.setAttribute("name2", name2);	
//		System.out.println(name1 + " " + name2);
		
//		새로운 객체가 매개변수로 지정되어 전달되어지는 RequestMapping 메서드에서는 이전 메서드에서 저장해놨던
//		model 과 request 가 적용되지 않음
//		test2 메서드에서 보내려고 했던 model 과 request 는 전달인수로 보낸게 아니기도 하고
//		test3 메서드에서 매개변수는 새로운 request 와 model 이 채워지기 때문임
//		request 와 model 의 내용을 꺼내쓴는건 return 되는 jsp 파일내부만 가능함
		
		model.addAttribute("name1", name4);
		request.setAttribute("name2", name5);
		
		return "test3";
	}
	
	@RequestMapping("/test4")
	public ModelAndView test4() {
		
		ModelAndView mav = new ModelAndView();
		// ModelAndView : 전달자료도 저장하고, 이동할 페이지도 지정해서 한번에 리턴 전달을 가능하게 하는 클래스임
		ArrayList<String> list = new ArrayList<String>();
		list.add("test1");
		list.add("test2");
		list.add("test3");
		
		// 전송할 자료 저장(키(String), value(Object))
		mav.addObject("lists", list);
		mav.addObject("ObjectTest", "테스트임");
		mav.addObject("name", "홍길동");
		
		// 이동할 jsp 페이지 지정
		mav.setViewName("myView");
		
		
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
