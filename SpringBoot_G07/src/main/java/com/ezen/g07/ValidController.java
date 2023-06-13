package com.ezen.g07;

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
	public String create(@ModelAttribute("dto") ContentDto contentdto, BindingResult result, Model model) {

		ContentValidator valid = new ContentValidator();
		valid.validate(contentdto, result);

		if (result.hasErrors()) {
			if (result.getFieldError("writer") != null) {
				model.addAttribute("message", "writer가 비어있습니다.");
			} else {
				model.addAttribute("message", "content 를 써주세요");
			}
			return "startPage";
		} else {
			return "DonePage";
		}

	}

}
