package com.ezen.g16.comtroller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletContext;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g16.dto.BoardVO;
import com.ezen.g16.dto.Paging;
import com.ezen.g16.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	BoardService bs;

	@RequestMapping("/main")
	public ModelAndView goMain(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") == null)
			mav.setViewName("loginform");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			paramMap.put("ref_cursor", null);

			bs.selectBoard(paramMap);

			ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			mav.addObject("boardList", list);
			mav.addObject("paging", (Paging) paramMap.get("paging"));
			mav.setViewName("board/main");

		}
		return mav;
	}

	@RequestMapping("/boardView")
	public ModelAndView boardView(@RequestParam("num") int num, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("num", num);
		paramMap.put("ref_cursor1", null);
		paramMap.put("ref_cursor2", null);

		bs.getBoard(paramMap);

		ArrayList<HashMap<String, Object>> list1 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor1");
		ArrayList<HashMap<String, Object>> list2 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor2");

		mav.addObject("board", list1.get(0));
		mav.addObject("replyList", list2);

		mav.setViewName("board/boardView");
		return mav;
	}

	@RequestMapping("/addReply")
	public String addReply(@RequestParam("boardnum") int boardnum, @RequestParam("userid") String userid,
			@RequestParam("content") String content, HttpServletRequest request) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("content", content);
		paramMap.put("boardnum", boardnum);

		bs.insertReply(paramMap);

		return "redirect:/boardViewWithoutCount?num=" + boardnum;

	}

	@RequestMapping("/boardViewWithoutCount")
	public ModelAndView boardViewWithoutCount(@RequestParam("num") int num, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("num", num);
		paramMap.put("ref_cursor1", null);
		paramMap.put("ref_cursor2", null);

		bs.getBoardWithoutCount(paramMap);

		ArrayList<HashMap<String, Object>> list1 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor1");
		ArrayList<HashMap<String, Object>> list2 = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor2");

		mav.addObject("board", list1.get(0));
		mav.addObject("replyList", list2);

		mav.setViewName("board/boardView");
		return mav;
	}

	@RequestMapping("/deleteReply")
	public String reply_delete(@RequestParam("num") int replynum, @RequestParam("boardnum") int boardnum,
			HttpServletRequest request) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("replynum", replynum);
		bs.deleteReply(paramMap);

		return "redirect:/boardViewWithoutCount?num=" + boardnum;
	}

	@RequestMapping("/boardWriteForm")
	public String write_form(HttpServletRequest request) {

		String url = "board/boardWriteForm";
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") == null)
			url = "member/loginForm";

		return url;
	}

	@RequestMapping("/selectimg")
	public String selectimg() {
		return "board/selectimg";
	}

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String fileupload(@RequestParam("image") MultipartFile file, Model model, HttpServletRequest request) {

		String path = context.getRealPath("/upload");

//		System.out.println("path=" +path);
//		System.out.println("multipartFile=" +file);

		Calendar today = Calendar.getInstance();
		long t = today.getTimeInMillis();
		String filename = file.getOriginalFilename(); // 파일이름 추출
		String fn1 = filename.substring(0, filename.indexOf(".")); // 파일이름과 확장자 분리
		String fn2 = filename.substring(filename.indexOf(".") + 1);

		if (!file.isEmpty()) { // 업로드 할 파일이 존재한다면
			String uploadPath = path + "/" + fn1 + t + "." + fn2;
			System.out.println("파일 저장 경로 = " + uploadPath);
			try {
				file.transferTo(new File(uploadPath));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("image", fn1 + t + "." + fn2);
		return "board/completupload";

	}

	@RequestMapping(value = "/boardWrite", method = RequestMethod.POST)
	public String board_write(@ModelAttribute("dto") @Valid BoardVO boardvo, BindingResult result, Model model,
			HttpServletRequest request) {

		String url = "board/boarWriteForm";
		if (result.getFieldError("pass") != null)
			model.addAttribute("message", result.getFieldError("pass").getDefaultMessage());
		else if (result.getFieldError("title") != null)
			model.addAttribute("message", result.getFieldError("title").getDefaultMessage());
		else if (result.getFieldError("content") != null)
			model.addAttribute("message", result.getFieldError("content").getDefaultMessage());
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", boardvo.getUserid());
			paramMap.put("pass", boardvo.getPass());
			paramMap.put("email", boardvo.getEmail());
			paramMap.put("content", boardvo.getContent());
			paramMap.put("title", boardvo.getTitle());
			if (boardvo.getImgfilename() == null)
				paramMap.put("imgfilename", "");
			else
				paramMap.put("imgfilename", boardvo.getImgfilename());
			bs.insertBoard(paramMap);
			url = "redirect:/main";
		}
		return url;

	}

	@RequestMapping("/boardEditForm")
	public String board_edit_form(@RequestParam("num") String num, Model model, HttpServletRequest request) {

		model.addAttribute("num", num);
		return "board/boardCheckPassForm";
	}

	@RequestMapping("/boardEdit")
	public String board_edit(@RequestParam("num") int num, @RequestParam("pass") String pass, Model model,
			HttpServletRequest request) {

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("num", num);
		paramMap.put("ref_cursor1", null);
		paramMap.put("ref_cursor2", null); // getBoardWithoutCount 매개변수에 전달인수 갯수를 맞춤

		bs.getBoardWithoutCount(paramMap);

		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor1");

		HashMap<String, Object> bvo = list.get(0);

		model.addAttribute("num", num);
		if (pass.equals(bvo.get("PASS")))
			return "board/boardCheckPass";
		else {
			model.addAttribute("message", "비밀번호가 맞지 안음. 확인");
			return "board/boardCheckPassForm";
		}
	}

	@RequestMapping("boardUpdateForm")
	public String board_update_form(@RequestParam("num") int num, Model model, HttpServletRequest request) {

		// num 으로 조회해서 dto 에 담고 "board/boardUpdateForm"으로 이동함
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("num", num);
		paramMap.put("ref_cursor1", null);
		paramMap.put("ref_cursor2", null); // getBoardWithoutCount 매개변수에 전달인수 갯수를 맞춤
		bs.getBoardWithoutCount(paramMap);
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor1");
		HashMap<String, Object> bvo = list.get(0);

		// 조회한 게시물을 dto 로 옮겨 담음
		BoardVO dto = new BoardVO();
		dto.setNum(Integer.parseInt(String.valueOf(bvo.get("NUM"))));
		dto.setPass((String) bvo.get("PASS"));
		dto.setUserid((String) bvo.get("USERID"));
		dto.setTitle((String) bvo.get("TITLE"));
		dto.setContent((String) bvo.get("CONTENT"));
		dto.setEmail((String) bvo.get("EMAIL"));
		dto.setImgfilename((String) bvo.get("IMGFILENAME"));
		model.addAttribute("num", num);
		model.addAttribute("dto", dto);
		return "board/boardUpdateForm";

	}

	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdate(
			@ModelAttribute("dto") @Valid BoardVO boardvo, BindingResult result,
			@RequestParam(value = "oldfilename", required = false) String oldfilename, 
			HttpServletRequest request, Model model) {

		String url = "board/boardUpdateForm";

		if (result.getFieldError("pass") != null)
			model.addAttribute("message", "비밀번호는 게시물 수정 삭제시 필요합니다");
		else if (result.getFieldError("title") != null)
			model.addAttribute("message", "제목은 필수입력 사항입니다");
		else if (result.getFieldError("content") != null)
			model.addAttribute("message", "게시물 내용은 비워둘수 없습니다.");
		else {
			if (boardvo.getImgfilename() == null || boardvo.getImgfilename().equals(""))
				boardvo.setImgfilename(oldfilename);
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("num", boardvo.getNum());
			paramMap.put("userid", boardvo.getUserid());
			paramMap.put("pass", boardvo.getPass());
			paramMap.put("email", boardvo.getEmail());
			paramMap.put("content", boardvo.getContent());
			paramMap.put("title", boardvo.getTitle());
			paramMap.put("imgfilename", boardvo.getImgfilename());
			bs.updateBoard(paramMap);
			url = "redirect:/boardViewWithoutCount?num="+boardvo.getNum();
			
		}
		return url;
	}
	
	@RequestMapping("/boardDelete")
	public String board_delete(Model model, HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("num", num);
		bs.removeBoard(paramMap);

		return "redirect:/main";
	}
}
