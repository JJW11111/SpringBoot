package com.ezen.g17.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g17.dto.KakaoProfile;
import com.ezen.g17.dto.KakaoProfile.KakaoAccount;
import com.ezen.g17.dto.KakaoProfile.KakaoAccount.Profile;
import com.ezen.g17.dto.MemberVO;
import com.ezen.g17.dto.OAuthToken;
import com.ezen.g17.service.MemberService;
import com.google.gson.Gson;

@Controller
public class MemberController {

	@Autowired
	MemberService ms;

	@RequestMapping("loginForm")
	public String loginForm() {
		return "member/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result, Model model,
			HttpServletRequest request) {
		String url = "member/login";
		if (result.getFieldError("id") != null)
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
		else if (result.getFieldError("pwd") != null)
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", membervo.getId());
			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);

			ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");

			if (list == null || list.size() == 0) {
				model.addAttribute("message", "아이디가 없음");
				return "member/login";
			}
			HashMap<String, Object> mvo = list.get(0);
			if (mvo.get("PWD") == null)
				model.addAttribute("message", "비밀번호 오류. 관리자에게 문의하세요");
			else if (!mvo.get("PWD").equals(membervo.getPwd()))
				model.addAttribute("message", "비밀번호가 맞지않습니다");
			else if (mvo.get("PWD").equals(membervo.getPwd())) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", mvo);
				url = "redirect:/";
			}
		}
		return url;
	}

	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		return "redirect:/";
	}

	@RequestMapping("/kakaostart")
	public @ResponseBody String kakaostart() {
		String a = "<script type='text/javascript'>" + "location.href='https://kauth.kakao.com/oauth/authorize?"
				+ "client_id=8e3c47cfbcc4a50c90c6ba76c192a457&" + "redirect_uri=http://localhost:8070/kakaoLogin&"
				+ "response_type=code';" + "</script>";
		return a;
	}

	@RequestMapping("/kakaoLogin")
	public String loginKakao(HttpServletRequest request) throws UnsupportedEncodingException, IOException {

		// 카카오 아이디, 비번 인증 + 아이디 이메일 제공 동의 후 전송되는 암호화 코드
		String code = request.getParameter("code");
		// 전송된 암호화코드를 이용해서 토큰을 요청
		// 토큰 요청 주소 url 설정 및 파라미터
		String endpoint = "https://kauth.kakao.com/oauth/token";
		URL url = new URL(endpoint); // import java.net.URL;
		String bodyData = "grant_type=authorization_code&";
		bodyData += "client_id=8e3c47cfbcc4a50c90c6ba76c192a457&";
		bodyData += "redirect_uri=http://localhost:8070/kakaoLogin&";
		bodyData += "code=" + code;
		// Stream 연결 및 토큰 수신
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // import java.net.HttpURLConnection;
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoOutput(true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(bodyData);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String input = "";
		StringBuilder sb = new StringBuilder(); // 조각난 String 을 조립하기위한 객체
		while ((input = br.readLine()) != null) {
			sb.append(input);
			System.out.println(input); // 수신된 토큰을 콘솔에 출력합니다
		}

		Gson gson = new Gson();
		OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);

		// oAuthToken을 이용해서 사용자 정보를 요청 수신
		String endpoint2 = "https://kapi.kakao.com/v2/user/me";
		URL url2 = new URL(endpoint2);
		// import java.net.HttpURLConnection;
		HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
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
		KakaoAccount ac = kakaoProfile.getAccount();
		Profile pf = ac.getProfile();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", kakaoProfile.getId());
		paramMap.put("ref_cursor", null);
		ms.getMember(paramMap);
		// 해쉬맵에 담겨온 ref_cursor 의 형태는 해쉬맵들의 리스트이고, 해시맵 하나는 검색결과의 레코드 하나에 해당
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");

		if (list == null || list.size() == 0) {
			paramMap.put("id", kakaoProfile.getId());
			paramMap.put("email", ac.getEmail());
			paramMap.put("name", pf.getNickname());
			paramMap.put("provider", "kakao");
			ms.joinKakao(paramMap);

			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);
			list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		}

		HashMap<String, Object> mvo = list.get(0);

		HttpSession session = request.getSession();
		session.setAttribute("loginUser", mvo);

		return "redirect:/";
	}

	@RequestMapping("/contract")
	public String contract() {
		return "member/contract";
	}

	@RequestMapping(value = "/joinForm", method = RequestMethod.POST)
	public String join_form() {
		return "member/joinForm";
	}

	@RequestMapping("/idCheckForm")
	public ModelAndView id_check_form(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("ref_cursor", null);
		ms.getMember(paramMap);
		ArrayList<HashMap<String, Object>> list 
			= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");

		if (list == null || list.size() == 0)
			mav.addObject("result", -1);
		else
			mav.addObject("result", 1);
		mav.addObject("id", id);
		mav.setViewName("member/idcheck");
		
		return mav;
	}

	
	@RequestMapping(value = "join", method=RequestMethod.POST)
	public String join(
					@ModelAttribute("dto") @Valid MemberVO membervo,
					BindingResult result, Model model, HttpServletRequest request, 
					@RequestParam("reid") String reid, @RequestParam("pwdCheck") String pwdCheck) {
		
		model.addAttribute("reid", reid);
		String url = "member/joinForm";
		
		if( result.getFieldError("id")!=null) 
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
		else if( result.getFieldError("pwd")!=null) 
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		else if( result.getFieldError("name")!=null) 
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
		else if( result.getFieldError("email")!=null) 
			model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
		else if( !reid.equals(membervo.getId()) ) 
			model.addAttribute("message", "id 중복체크를 하지 않았습니다");
		else if( !pwdCheck.equals( membervo.getPwd() ) ) 
			model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다");
		else {
			model.addAttribute("message", "회원가입이 완료되었습니다. 로그인 하세요");
			ms.insertMember(membervo);
			url = "member/login";
		}
		return url;
	}
}