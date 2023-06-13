package com.ezen.g06;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ValidController {

	@RequestMapping("/")
	public String main() {
		return "startPage";
	}
	
	@RequestMapping("/create")
	public String create(@ModelAttribute("dto") ContentDto contentdto, Model model, BindingResult result) {
		
		// 전송된 값들을 검사해서 한개의 값이라도 비어있으면 startPage.jsp 로 되돌아 감
		// 정상적인 값들이 전송되었으면 DonePage.jsp 로 이동함
		
		// validation 기능이 있는 클래스르 ㄹ생성하고 그 객체를 이용해서 검사함
		// 클래스의 이름은 ContentValidator , java 의 Validator 인터페이스를 implements 한 클래스임
		ContentValidator valid =  new ContentValidator();
		valid.validate(contentdto, result);
		
		if(result.hasErrors()) {
			model.addAttribute("message", "writer 와 content 는 비어있으면 안됩니다");
			return "startPage";
		}else {
			return "DonePage";
		}
		
		
	}
	
}
