package com.ezen.g08;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ContentDto {
	@Size(min=4,max=20,message="Writer min 4, max 20")
	@NotEmpty(message="Writer is Empty")
	@NotNull(message="Writer is Null")
		private String writer;
	@NotEmpty(message="Content is Empty")
	@NotNull(message="Content is Null")
		private String content;
	
	//@NotEmpty 또는 @NotNull 같은 밸리데이션 어너테이션은 반드시 검사하고자 하는 멤버 변수에만 붙여 사용
	//필요없는 곳에 붙여 사용하게 되면 에러가 발생하는지 검사하지 않아도 되는 곳에서 에러발생
	private String name;

}
