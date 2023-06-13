package com.ezen.g07;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ContentDto dto = (ContentDto) target;
		// 전달객체의 멤버변수를 꺼내어 보지 않고 널이거나 비어 있는지 점검
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "writer", "wirter is empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "content is empty");
		
	}
}
