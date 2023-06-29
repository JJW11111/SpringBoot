package com.ezen.g17.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.g17.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	AdminService as;
	
	@RequestMapping(value="/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}
	
	@RequestMapping(value="/adminLogin")
	public String adminLogin( HttpServletRequest request, Model model, 
			@RequestParam("workId") String workId, 
			@RequestParam("workPwd") String workPwd) {	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("workId", workId);
		paramMap.put( "ref_cursor", null );
		as.getAdmin(paramMap);	 // 아이디로 관리자 조회
		ArrayList< HashMap<String,Object> > list 
			= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		String url = "admin/adminLoginForm";
		if(list.size() == 0) {  // 입력한 아이디 없다면
			model.addAttribute("message" , "아이디가 없어요");
			return "admin/adminLoginForm";
		}
		HashMap<String, Object> resultMap = list.get(0); 
		if(resultMap.get("PWD")==null) model.addAttribute("message" , "관리자에게 문의하세요");
		else if( !workPwd.equals( (String) resultMap.get("PWD") ) )
			model.addAttribute("message" , "비번이 안맞아요");
		else if( workPwd.equals( (String) resultMap.get("PWD") ) ) { 
			HttpSession session = request.getSession();
			session.setAttribute("loginAdmin", resultMap);
			url = "redirect:/productList";
		}
		return url;
	}
	
	@RequestMapping(value="/productList")
	public ModelAndView product_list(HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if( session.getAttribute("loginAdmin")==null) 
			mav.setViewName("admin/adminLoginForm");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			paramMap.put( "ref_cursor", null );
			as.getProductList( paramMap );
			
			ArrayList< HashMap<String,Object> > list 
				= (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			
			mav.addObject("productList", list);
			mav.addObject("paging", paramMap.get("paging") );
			mav.addObject("key", paramMap.get("key") );
			mav.setViewName("admin/product/productList");
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="/productWriteForm")
	public String product_write_form( HttpServletRequest request, Model model) {
		String kindList[] = { "Heels", "Boots", "Sandals", "Shcakers", "Slipers",  "Sale" };
		model.addAttribute("kindList", kindList);
		return "admin/product/productWriteForm";
	}
	
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value="fileup", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> fileup( 
			@RequestParam("fileimage") MultipartFile file,
			HttpServletRequest request, Model model	) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String path = context.getRealPath("/product_images");	
		Calendar today = Calendar.getInstance();
 		long t = today.getTimeInMillis();
 		String filename = file.getOriginalFilename(); // 파일이름 추출
 		String fn1 = filename.substring(0, filename.indexOf(".") );  // 파일이름과 확장장 분리
 		String fn2 = filename.substring(filename.indexOf(".")+1 );
 		
 		if (!file.isEmpty()) {   // 업로드할 파일이 존재한다면
            String uploadPath = path + "/" + fn1 + t +  "." + fn2;
            System.out.println("파일 저장 경로 = " + uploadPath);
            try {
				file.transferTo( new File(uploadPath) );
			} catch (IllegalStateException e) { e.printStackTrace();
			} catch (IOException e) { e.printStackTrace();
			}
 		}
		result.put("STATUS", 1);
		result.put("FILENAME", fn1 + t +  "." + fn2 );
		return result;
	}
	
	@RequestMapping(value="/productWrite" , method = RequestMethod.POST)
	public String productWrite(	Model model ,  HttpServletRequest request ) {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", request.getParameter("name") );
		paramMap.put("kind", request.getParameter("kind") );
		paramMap.put("price1", Integer.parseInt( request.getParameter("price1") ) );
		paramMap.put("price2", Integer.parseInt( request.getParameter("price2") ) );
		paramMap.put("price3", Integer.parseInt( request.getParameter("price3") ) );
		paramMap.put("content", request.getParameter("content") );
		if( request.getParameter("image") == null )
			paramMap.put("image", "" );
		else 
			paramMap.put("image", request.getParameter("image") );
		as.insertProduct( paramMap);
		return "redirect:/productList";
	}
	
}





















