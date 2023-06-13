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
	public String create(@ModelAttribute("dto") ContentDto contentdto,BindingResult result, Model model) {

		// 전송된 값들을 검사해서 한개의 값이라도 비어있으면 startPage.jsp 로 되돌아 감
		// 정상적인 값들이 전송되면 DonePage.jsp로 이동
		
		//validation 기능이 있는 클래스를 생성하고 그 객체를 이용해서 검사
		//클래스의 이름은 ContentValidator, java의 Validator 인터페이스를 implements한 클래스
		ContentValidator valid = new ContentValidator();
		valid.validate(contentdto, result);

		//BindingResult result : 에러 제목(키값)과 내용(밸류값)을 담을 수 있는 객체
		//validator의 멤버 메서드인 validate가 contentdto 내용을 검사한 후 result에 오류 내용을 담아주고
		//리턴되지 않아도 call by reference이기 때문에 오류내용을 현재 위치에서도 result라는 이름으로 사용이 가능
		
		
		
		if(result.hasErrors()) {
			model.addAttribute("message","writer와 content는 비어있으면 안됩니다");
			return "startPage";
		}else {
			return "DonePage";
		}

	}

}
