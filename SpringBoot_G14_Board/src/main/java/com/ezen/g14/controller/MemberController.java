package com.ezen.g14.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g14.dto.KakaoProfile;
import com.ezen.g14.dto.KakaoProfile.KakaoAccount;
import com.ezen.g14.dto.KakaoProfile.KakaoAccount.Profile;
import com.ezen.g14.dto.MemberVO;
import com.ezen.g14.dto.OAuthToken;
import com.ezen.g14.service.MemberService;
import com.google.gson.Gson;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;

	
	@RequestMapping("/")
	public String root() {
		return "member/loginForm";
	}

	// @ModelAttribute("dto") 실패했을때 가져가기위한것
	// BindingResult result 에러확인?
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("dto") @Valid MemberVO MemberVO, BindingResult result,
			HttpServletRequest request, Model model) {

		String url = "member/loginForm";
		if (result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
		} else if (result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		} else {
			// 아이디 비번이 정상입력
			MemberVO mvo = ms.getMember(MemberVO.getUserid());
			if (mvo == null) {
				model.addAttribute("message", "아이디가 없습니다");
				return "member/loginForm";
			}

			else if (mvo.getPwd() == null) {
				model.addAttribute("message", "비밀번호 오류. 관리자에게 문의하세요");
				return "member/loginForm";
			}

			else if (!mvo.getPwd().equals(MemberVO.getPwd())) {
				model.addAttribute("message", "비밀번호가 맞지않습니다");
				return "member/loginForm";
			}

			else if (mvo.getPwd().equals(MemberVO.getPwd())) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", mvo);
				url = "redirect:/main";
			}

		}

		return url;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/kakaoLogin")
	public String login(HttpServletRequest request) throws UnsupportedEncodingException, IOException {

		String code = request.getParameter("code");

		String endpoint = "https://kauth.kakao.com/oauth/token";
		URL url = new URL(endpoint);

		String bodyData = "grant_type=authorization_code&";
		bodyData += "client_id=8e3c47cfbcc4a50c90c6ba76c192a457&";
		bodyData += "redirect_uri=http://localhost:8070/kakaoLogin&";
		bodyData += "code=" + code;

		// Stream 연결
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoOutput(true);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(bodyData);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String input = "";
		StringBuilder sb = new StringBuilder();
		while ((input = br.readLine()) != null) {
			sb.append(input);
			System.out.println(input);
		}

		// 여기서부터Gson으로 파싱
		Gson gson = new Gson();

		OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
		String endpoint2 = "https://kapi.kakao.com/v2/user/me";
		URL url2 = new URL(endpoint2);

		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();

		conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
		conn2.setDoOutput(true);

		BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
		String input2 = "";
		StringBuilder sb2 = new StringBuilder();
		while ((input2 = br2.readLine()) != null) {
			sb2.append(input2);
			System.out.println(input2);
		}

		Gson gson2 = new Gson();
		KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);

		System.out.println(kakaoProfile.getId());

		KakaoAccount ac = kakaoProfile.getAccount();
		System.out.println(ac.getEmail());

		Profile pf = ac.getProfile();
		System.out.println(pf.getNickname());

		// kakao 로부터 얻은 정보로 member 테이블에서 조회
		MemberVO mvo = ms.getMember(kakaoProfile.getId());
		if (mvo == null) {
			mvo = new MemberVO();
			mvo.setUserid(kakaoProfile.getId());
			mvo.setEmail(ac.getEmail());
			mvo.setName(pf.getNickname());
			mvo.setProvider("kakao");
			mvo.setPwd("kakao");
			mvo.setPhone("");

			ms.insertMember(mvo);
			
		}

		HttpSession session = request.getSession();
		session.setAttribute("loginUser", mvo);

		return "redirect:/main";
	}
	
		@RequestMapping("/kakaostart")
		public @ResponseBody String kakaostart() {
			String a = "<script type='text/javascript'>"
					+ "location.href='https://kauth.kakao.com/oauth/authorize?client_id=8e3c47cfbcc4a50c90c6ba76c192a457&redirect_uri=http://localhost:8070/kakaoLogin&response_type=code'"
					+ "</script>";
			return a;
		}
		
		@RequestMapping("/memberJoinForm")
		public String memberJoinForm() {
			return "member/memberJoinForm";
		}
		
		@RequestMapping("/idcheck")
		public ModelAndView idcheck(@RequestParam("userid") String userid) {
			
			ModelAndView mav = new ModelAndView();
			
			MemberVO mvo = ms.getMember(userid);
			if( mvo == null )mav.addObject("result", -1);
			else mav.addObject("result", 1);
			mav.addObject("userid", userid);
			mav.setViewName("member/idcheck");
			return mav;
		}
		
		@PostMapping("/memberJoin")
		public ModelAndView memberJoin(
				@ModelAttribute("dto") @Valid MemberVO membervo, 
				BindingResult result,
				@RequestParam(value = "re_id", required=false) String re_id,
				@RequestParam(value = "pwd_check", required=false) String pwd_check) {
			ModelAndView mav = new ModelAndView();
			// 벨리데이션으로 전송된 값들을 점검하고, 널이나 빈칸이 있으면 memberJoinForm.jsp 로 되돌아 가세요
			// MemberVO 로 자동되지 않는 전달인수 - pwd_check , re_id 들은 별도의 변수로 전달받고, 별도로 이상유무를
			// 체크하고 이상이 있을시 memberJoinForm.jsp 로 되돌아 가세요.
			// 이 때 re_id 도 mav 에 별도 저장하고 되돌아 감
			// 모두 이상이 없다고 점검이 되면 회원가입하고, 회원가입 완료라는 메세지와 함께 loginForm.jsp 로 되돌아 가세요
			
			mav.setViewName("member/memberJoinForm"); // 되돌아 갈 페이지의 기본은 회원가입 페이지\
			
			mav.addObject("re_id", re_id);
			
			if(result.getFieldError("userid") != null ) 
				mav.addObject("message", result.getFieldError("userid").getDefaultMessage());
			else if(result.getFieldError("name") != null ) 
				mav.addObject("message", result.getFieldError("name").getDefaultMessage());
			else if(result.getFieldError("pwd") != null ) 
				mav.addObject("message", result.getFieldError("pwd").getDefaultMessage());
			else if(result.getFieldError("email") != null ) 
				mav.addObject("message", result.getFieldError("email").getDefaultMessage());
			else if(result.getFieldError("phone") != null ) 
				mav.addObject("message", result.getFieldError("phone").getDefaultMessage());
			else if(re_id == null || !membervo.getUserid().equals(re_id))
				mav.addObject("message", "아이디 중복체크가 되지 않았습니다.");
			else if(!membervo.getPwd().equals(pwd_check))
				mav.addObject("message", "비밀번호 확인이 일치하지 않습니다.");
			else {
				ms.insertMember(membervo);
				mav.addObject("message", "회원가입이 완료되었습니다. 로그인하세요.");
				mav.setViewName("member/loginForm");
			}			

			return mav;
			
		}
				
		
		@RequestMapping("/memberEditForm")
		public ModelAndView memberEditForm(HttpServletRequest request) {
			ModelAndView mav = new ModelAndView();
			
			HttpSession session = request.getSession();
			MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
			mav.addObject("dto", mvo);
			
			mav.setViewName("member/memberEditForm");
			return mav;
		}
		
		
		@RequestMapping(value = "/memberEdit", method=RequestMethod.POST)
		public String memberEdit(
				@ModelAttribute("dto") @Valid MemberVO membervo,
				BindingResult result,
				@RequestParam(value="pwd_check", required=false) String pwdchk,
				Model model,
				HttpServletRequest request) {
			String url = "member/memberEditForm";
			
			// 비밀번호, 비밀번호 확인, 이메일, 전화번호를 확인 후 회원 정보를 수정합니다.
			// 회원 정보 수정 후 세션의 loginUser 수정 후 main 으로 이동함
			if (result.getFieldError("pwd") != null) {
				model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
			} else if (result.getFieldError("name") != null) {
				model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
			} else if (result.getFieldError("email") != null) {
				model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
			} else if (result.getFieldError("phone") != null) {
				model.addAttribute("message", result.getFieldError("phone").getDefaultMessage());
			} else if (!membervo.getPwd().equals(pwdchk))
				model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다");
			else {
				ms.updateMember(membervo);
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", membervo);
				url = "redirect:/main";
			}
			return url;
		}
		
		

		
		
		
		
		
}