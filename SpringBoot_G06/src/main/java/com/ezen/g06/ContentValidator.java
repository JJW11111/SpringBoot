package com.ezen.g06;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}//거의 사용할 일 없음

	@Override
	public void validate(Object target, Errors errors) {
		
		//object target : 검사할 객체(dto)를 받아주는 매개변수(Object형). 전달된 객체 멤버변수가 비어있는 지 검사예정
		//Error errors : 보내온 객체에 에러내용을 담아서 다시 보내줄 객체 매개변수
		ContentDto dto = (ContentDto) target;
		String sWriter = dto.getWriter();
		String sContent = dto.getContent();
		
		//null값(new String()조차도 실행안됨)이거나 값이 비어있거나("")
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
