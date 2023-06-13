package com.ezen.g02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// Model2 MVC 에서  프로젝트의 요청을담당하는 서블릿과  기능별 구동을 담당하는 ActionFactory 역할을 하는클래스입니다
// 현재클래스가 그 역할을 하는 클래스라는걸 알려주기위해  아래와 같은 어너테이션을 사용하여 표시합니다

@Controller
public class SpringBootController {

	// 메서드 하나 하나가 ActionFactory와 각 연결된 클래스의  execute 역할을 합니다.
	// 따라서 각 메서드 마다 요청 메세지를 구분하는 어너테이션이 장착됩니다
	
	@RequestMapping("/")
	public @ResponseBody String root() {
		return "<h1>JSP in Gradle~!!</h1>";
	}
	
	@RequestMapping("/main")
	public String main() {
		return "main";
	}
	
	@RequestMapping("/sub")
	public String sub() {
		return "sub/sub";
	}
}









