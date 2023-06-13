package com.ezen.g06;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return false;
	}	// 얘는 거의 사용될 일 없음

	@Override
	public void validate(Object target, Errors errors) {
		
		// Object target : 검사할 객체(dto)를 받아주는 매개변수(Object 형). 전달된 객체내의 멤버변수가 비어있는지 검사 예정
		// Errors errors : 보내온 객체에 여러내용을 담아서 다시 보내줄 매개변수
		
		ContentDto dto = (ContentDto)target;
		String sWriter = dto.getWriter();
		String sContent = dto.getContent();
		
		// 널(new String() 조차도 실행안된거)이거나 값이 비어있거나("")
		if(sWriter == null || sWriter.trim().isEmpty()) {
			System.out.println("Writer is null or empty");
			errors.rejectValue("wrtier", "trouble");
		}
		
		if(sContent == null || sContent.trim().isEmpty()) {
			System.out.println("Content is null or empty");
			errors.rejectValue("content", "trouble");
		}
		
	}

}
