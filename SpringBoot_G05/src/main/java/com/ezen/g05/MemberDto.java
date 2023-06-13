package com.ezen.g05;

import lombok.Data;

@Data
public class MemberDto {
	
	private String id;
	private String name;
	
}

// 아래는 @Data 를 사용하지 않고 별도로 사용할 수 있는 어너테이션
// @Setter : Class 모든 필드의 Setter Method를 생성해줌
// @Getter : Class 모든 필드의 Getter Method를 생성해줌
// @AllArgsConstructor : Class 모든 필드 값을 파라미터로 받는 생성자를 추가함
// @NoArgsConstructor : Class 기본 생성자를 자동으로 추가해줌
// @ToString : Class 모든 필드의 toString method 를 생성함
