package com.ezen.g14.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g14.dto.BoardVO;
import com.ezen.g14.dto.Paging;
import com.ezen.g14.dto.ReplyVO;
import com.ezen.g14.service.BoardService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class BoardController {

	@Autowired
	BoardService bs;
	
	@Autowired
	ServletContext context;
	
	@RequestMapping("/main")
	public ModelAndView main( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if( session.getAttribute("loginUser") == null)	
			mav.setViewName("member/loginForm");
		else {
			// page 파라미터를 품고 있는 request service 에 보내서 페이지 처리 및 해당 게시물 조회후 
			// HashMap 에 모두 담아 리턴받을 예정입니다.
			HashMap<String, Object> result = bs.getBoardList( request );		
			//List<BoardVO> list = ( List<BoardVO> )result.get("boardList");	
			//Paging paging = (Paging) result.get("paging");
			mav.addObject("boardList", ( List<BoardVO> ) result.get("boardList") );
			mav.addObject("paging", ( Paging ) result.get("paging") );
			mav.setViewName("board/main");
		}
		return mav;
	}
	
	
	@RequestMapping("/boardView")
	public ModelAndView boardView( @RequestParam("num") int num ) {
		ModelAndView mav = new ModelAndView();
		
		HashMap<String, Object> result = bs.boardView( num );
		mav.addObject( "board" ,  result.get("board") );
		mav.addObject("replyList" , result.get("replyList") );
		
		mav.setViewName("board/boardView");
		return mav;
	}
	
	
	@RequestMapping("/boardWriteForm")
	public String write_form(HttpServletRequest request) {
		
		String url = "board/boardWriteForm";
		HttpSession session = request.getSession();
		if( session.getAttribute("loginUser") == null)	url="member/loginForm";		
		
		return url;
	}
	
	
	/*
	@RequestMapping( value="/boardWrite", method=RequestMethod.POST)
	public String boardWrite(	HttpServletRequest request  ) {
		//HttpSession session = request.getSession();
		//ServletContext context = session.getServletContext();
		//String path=context.getRealPath("upload");
		
		String path = context.getRealPath("/upload");
		BoardVO bvo = new BoardVO();
		try {
			MultipartRequest multi = new MultipartRequest(
					request, path, 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy()
			);
			bvo.setUserid( multi.getParameter("userid") );
			bvo.setPass( multi.getParameter("pass") );
			bvo.setTitle( multi.getParameter("title") );
			bvo.setEmail( multi.getParameter("email") );
			bvo.setContent( multi.getParameter("content") );
			if( multi.getFilesystemName("imgfilename") == null )  bvo.setImgfilename("");
			else  bvo.setImgfilename( multi.getFilesystemName("imgfilename") );
			bs.insertBoard( bvo );
		} catch (IOException e) { e.printStackTrace();
		}
		return "redirect:/main";
	}*/
	
	
	@RequestMapping( value="/boardWrite", method=RequestMethod.POST)
	public String boardWrite(	
						@ModelAttribute("dto") @Valid BoardVO boardvo,
						BindingResult result,
						Model model ) {
		String url = "board/boardWriteForm";
		if( result.getFieldError("pass") != null ) 
			model.addAttribute("message", result.getFieldError("pass").getDefaultMessage() );
		else if( result.getFieldError("title")!=null)
			model.addAttribute("message", result.getFieldError("title").getDefaultMessage() );
		else if( result.getFieldError("content")!=null)
			model.addAttribute("message", result.getFieldError("content").getDefaultMessage() );
		else {
			if( boardvo.getImgfilename() == null) boardvo.setImgfilename("");
			bs.insertBoard(boardvo);
			url = "redirect:/main";
		}
		return url;
	}
	
	
	@RequestMapping("/selectimg")
	public String selectimg() {
		return "board/selectimg";
	}
	
	
	@RequestMapping(value="/fileupload" , method = RequestMethod.POST)
	public String fileupload(Model model, HttpServletRequest request) {
		
		String path = context.getRealPath("/upload");
		try {
			MultipartRequest multi = new MultipartRequest(
					request, path, 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy()
			);
			// 전송된 파일은 업로드 되고, 파일 이름은  모델에 저장합니다
			model.addAttribute("image", multi.getFilesystemName("image") );
		} catch (IOException e) {  e.printStackTrace();
		}
		return "board/completupload";
		
	}
	
	
	@RequestMapping("/addReply")
	public String addReply(	 ReplyVO replyvo 
					//@RequestParam("boardnum") int boardnum, 
					//@RequestParam("userid") String userid, 
					//@RequestParam("content") String content  
			) {
			
		bs.insertReply( replyvo );
		return "redirect:/boardViewWithoutCount?num=" + replyvo.getBoardnum();
	}

	
	@RequestMapping("/boardViewWithoutCount")
	public ModelAndView boardViewWithoutCount( @RequestParam("num") int num ) {
		ModelAndView mav = new ModelAndView();
		
		HashMap<String, Object> result = bs.boardViewWithoutCount( num );
		mav.addObject( "board" ,  result.get("board") );
		mav.addObject("replyList" , result.get("replyList") );
		
		mav.setViewName("board/boardView");
		return mav;
	}
	
	
	@RequestMapping("/deleteReply")
	public String reply_delete( 
			@RequestParam("num") int num, 
			@RequestParam("boardnum") int boardnum,
			HttpServletRequest request
	) {
		// 전달된 num 값을 이용하여 댓글을 삭제해주세요
		// 필요한 매개변수를 자유롭게 생성하여 제작합니다
		// 댓글 삭제 후 조회수 증가없이 해당 게시글의 페이지로 이동하세요
		bs.deleteReply(num);
		return "redirect:/boardViewWithoutCount?num=" + boardnum;
		
	}
	
	
	
	@RequestMapping("/boardEditForm")
	public String board_edit_form(Model model, HttpServletRequest request) {
		String num = request.getParameter("num");
		model.addAttribute("num", num);
		return "board/boardCheckPassForm";
	}
	
	
	
	@RequestMapping("/boardEdit")
	public String board_edit(
			@RequestParam("num") int num,
			@RequestParam("pass") String pass, 
			Model model, 
			HttpServletRequest request) {
		
		BoardVO bvo = bs.getBoard(num);
		model.addAttribute("num", num);
		
		if( pass.equals(bvo.getPass())  ) {
			return "board/boardCheckPass";
		}	else {
			model.addAttribute("message", "비밀번호가 맞지 않습니다. 확인해주세요");
			return "board/boardCheckPassForm";
		}
	}
	
	@RequestMapping("/boardUpdateForm")
	public String board_update_form(	@RequestParam("num") int num,	Model model ) {
		BoardVO bvo = bs.getBoard(num);
		model.addAttribute("num", num);
		model.addAttribute("dto", bvo);
		return "board/boardUpdateForm";
	}
	
	
	
	
	@RequestMapping(value="/boardUpdate", method = RequestMethod.POST)
	public String boardUpdate( 
							@ModelAttribute("dto") @Valid BoardVO boardvo,
							BindingResult result, 
							@RequestParam("oldfilename") String oldfilename, 
							HttpServletRequest request, Model model) {
		
		String url = "board/boardUpdateForm";
		
		if( result.getFieldError("pass")!=null) 
			model.addAttribute("message" , "비밀번호는 게시물 수정 삭제시 필요합니다");
		else  if(result.getFieldError("title")!=null) 
			model.addAttribute("message" , "제목은 필수입력 사항입니다");
		else if(result.getFieldError("content")!=null) 
			model.addAttribute("message" , "게시물 내용은 비워둘수 없습니다.");
		else {
			if( boardvo.getImgfilename()==null || boardvo.getImgfilename().equals("") )	
				boardvo.setImgfilename(oldfilename);
			bs.updateBoard( boardvo );
			url = "redirect:/boardViewWithoutCount?num=" + boardvo.getNum();
		}	
		return url;
	}
	
	
	
	@RequestMapping("boardDelete")
	public String board_delete(Model model, HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		bs.removeBoard(num);	
		return "redirect:/main";
	}
	
	
			
	
}












