package com.ezen.g15.controller;

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

import com.ezen.g15.dto.BannerVO;
import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.dto.OrderVO;
import com.ezen.g15.dto.Paging;
import com.ezen.g15.dto.ProductVO;
import com.ezen.g15.dto.QnaVO;
import com.ezen.g15.service.AdminService;
import com.ezen.g15.service.ProductService;
import com.ezen.g15.service.QnaService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class AdminController {

	@Autowired
	AdminService as;
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}
	
	
	@RequestMapping("adminLogin")
	public ModelAndView admin_login(
						@RequestParam(value="workId", required=false) String workId,
						@RequestParam(value="workPwd", required=false) String workPwd, 
						HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		if( workId == null ) {
			mav.addObject("msg" , "아이디를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}else if( workId.equals("") ) {
			mav.addObject("msg" , "아이디를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}else if( workPwd == null) {
			mav.addObject("msg" , "패쓰워드를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}else if( workPwd.equals("")) {
			mav.addObject("msg" , "패쓰워드를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}
		
		int result = as.workerCheck( workId, workPwd );
		
		if(result == 1) {
	    		HttpSession session = request.getSession();
	    		session.setAttribute("workId", workId);
	    		mav.setViewName("redirect:/productList");
		} else if (result == 0) {
	        	mav.addObject("message", "비밀번호를 확인하세요.");
	        	mav.setViewName("admin/adminLoginForm");
		} else if (result == -1) {
	    		mav.addObject("message", "아이디를 확인하세요.");
	    		mav.setViewName("admin/adminLoginForm");
		}	
		
		return mav;
	}
	
	
	@RequestMapping("/productList")
	public ModelAndView product_list( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("workId");
		if(id==null)
			mav.setViewName("redirect:/admin");
		else {
			HashMap<String, Object> result =  as.getProductList( request );
			mav.addObject("productList",  (List<ProductVO>)result.get("productList")  );
			mav.addObject("paging", (Paging)result.get("paging") );
			mav.addObject("key", (String)result.get("key") );
			mav.setViewName("admin/product/productList");
			// Controller 는 Service 가 작업해서 보내준 결과들을 mav 에 잘 넣어서 목적지로 이동만 합니다.
		}
		return mav;
	}
	
	
	
	@RequestMapping("/productWriteForm")
	public String product_write_form( HttpServletRequest request, Model model) {
		String kindList[] = { "Heels", "Boots", "Sandals", "Shcakers", "Slipers",  "Sale" };
		model.addAttribute("kindList", kindList);
		return "admin/product/productWriteForm";
	}
	
	
	
	@Autowired
	ServletContext context;
	
	
	@RequestMapping(value="/fileup", method=RequestMethod.POST)
	@ResponseBody   // 호출했던 위치로 되돌아가는 어너테이션
	public HashMap<String, Object> fileup(Model model, HttpServletRequest request) {
		
		// 현재 메서드는 다른 메서드처럼 jsp 파일이름을 리턴해서  파일이름.jsp 로 이동하는 메서드가 "아닙니다."
		// ajax 에의해서 호출된 지점으로 다시 되돌아가서 화면 이동없이 운영이 계속되어야 하기때문에 
		// 이동할때 가져갈 데이터가 리턴됩니다.   따라서 리턴될 데이터들은 여러개가 있을수 있으므로 해시맵에 담겨서 리턴될 예정입니다
		String path = context.getRealPath("/product_images");
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {
			MultipartRequest multi = new MultipartRequest(
					request, path, 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy()
			);
			result.put("STATUS", 1);
			result.put("FILENAME", multi.getFilesystemName("fileimage") );
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/productWrite" , method = RequestMethod.POST)
	public String productWrite( @ModelAttribute("dto") @Valid ProductVO productvo, 	
			BindingResult result, Model model ,  HttpServletRequest request) {
		
		String url = "admin/product/productWriteForm";
		if( result.getFieldError("name")!=null )
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage() );
		else if( result.getFieldError("price2")!=null )
			model.addAttribute("message", result.getFieldError("price2").getDefaultMessage() );
		else if( result.getFieldError("content")!=null )
			model.addAttribute("message", result.getFieldError("content").getDefaultMessage() );
		else if( result.getFieldError("image")!=null )
			model.addAttribute("message", result.getFieldError("image").getDefaultMessage() );
		else {
			as.insertProduct( productvo );
			url = "redirect:/productList";
		}
		return url;
	}
	
	
	
	@Autowired
	ProductService ps;
	
	@RequestMapping("adminProductDetail")
	public ModelAndView product_detail( 
			HttpServletRequest request, 
			@RequestParam("pseq") int pseq) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject( "productVO",   ps.getProduct( pseq ) );
		
		String kindList[] = { "0", "Heels", "Boots", "Sandals", "Slipers", "Shcakers", "Sale" };
		int index = Integer.parseInt( ps.getProduct(pseq).getKind()  /*pvo.getKind()*/) ;
		
		mav.addObject("kind", kindList[index]);
		mav.setViewName("admin/product/productDetail");
		
		return mav;
		
	}
	
	
	
	
	@RequestMapping("productUpdateForm")
	public ModelAndView product_update_form(
					Model model, HttpServletRequest request,
					@RequestParam("pseq") int pseq) {
		ModelAndView mav = new ModelAndView();
		model.addAttribute("productVO",   ps.getProduct(pseq) );
		String kindList[] = { "Heels", "Boots", "Sandals", "Slipers", "Shcakers", "Sale" };    
		mav.addObject("kindList", kindList);
		mav.setViewName("admin/product/productUpdate");
		
		return mav;
		
	}
	
	
	@RequestMapping(value="productUpdate" , method = RequestMethod.POST)
	public String productUpdate( 
					@ModelAttribute("dto") @Valid ProductVO productvo, 
					BindingResult result,	Model model , HttpServletRequest request) {
		
		String url = "admin/product/productUpdate";
		if( result.getFieldError("name")!=null )
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage() );
		else if( result.getFieldError("price2")!=null )
			model.addAttribute("message", result.getFieldError("price2").getDefaultMessage() );
		else if( result.getFieldError("content")!=null )
			model.addAttribute("message", result.getFieldError("content").getDefaultMessage() );
		else if( result.getFieldError("image")!=null )
			model.addAttribute("message", result.getFieldError("image").getDefaultMessage() );
		else {
			
			if( request.getParameter("bestyn") != null )	productvo.setBestyn("Y");
			else 	productvo.setBestyn("N");
			
			if( request.getParameter("useyn") != null )	productvo.setUseyn("Y");
			else 	productvo.setUseyn("N");
			
			if( productvo.getImage() == null  || productvo.getImage().equals("") )
				productvo.setImage( request.getParameter("oldfilename") );
			as.updateProduct( productvo );
			url = "redirect:/adminProductDetail?pseq=" + productvo.getPseq();
		}
		
		return url;
	}
	
	
	
	
	@RequestMapping("/adminOrderList")
	public ModelAndView adminOrderList( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("workId");
		if(id==null)
			mav.setViewName("redirect:/admin");
		else {
			HashMap<String, Object> result =  as.getOrderList( request );
			mav.addObject("orderList",  (List<OrderVO>)result.get("orderList")  );
			mav.addObject("paging", (Paging)result.get("paging") );
			mav.addObject("key", (String)result.get("key") );
			mav.setViewName("admin/order/orderList");
		}
		
		return mav;
	}
	
	
	
	@RequestMapping("/memberList")
	public ModelAndView memberList( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("workId");
		if(id==null)
			mav.setViewName("redirect:/admin");
		else {
			HashMap<String, Object> result =  as.getMemberList( request );
			mav.addObject("memberList",  (List<MemberVO>)result.get("memberList")  );
			mav.addObject("paging", (Paging)result.get("paging") );
			mav.addObject("key", (String)result.get("key") );
			mav.setViewName("admin/member/memberList");
		}
		
		return mav;
	}
	
	@RequestMapping("/adminQnaList")
	public ModelAndView adminQnaList( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("workId");
		if(id==null)
			mav.setViewName("redirect:/admin");
		else {
			HashMap<String, Object> result =  as.getQnaList( request );
			mav.addObject("qnaList",  (List<QnaVO>)result.get("qnaList")  );
			mav.addObject("paging", (Paging)result.get("paging") );
			mav.addObject("key", (String)result.get("key") );
			mav.setViewName("admin/qna/qnaList");
		}
		
		return mav;
	}
	
	
	@RequestMapping("/orderUpdateResult")
	public String orderUpdateResult( @RequestParam("result") int [] results  ) {
		
		// 전달된 results 속에 담긴 odseq 들을 하나씩 검색해서 result 값을 다음값으로 변경
		as.updateOrderResult( results );
		
		return "redirect:/adminOrderList";
	}
	
	
	
	
	@RequestMapping("/memberReinsert")
	public String memberReinsert( @RequestParam("id") String id, 
			@RequestParam("useyn") String useyn	) {
		
		if( useyn.equals("Y") ) useyn="N";
		else useyn="Y";
		
		as.memberReinsert( id, useyn );
		return "redirect:/memberList";
	}
	
	
	@Autowired
	QnaService qs;
	
	@RequestMapping("/adminQnaView")
	public ModelAndView adminQnaView( @RequestParam("qseq") int qseq   	) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("qnaVO", qs.getQna(qseq) );
		mav.setViewName("admin/qna/qnaView");
		return mav;
	}
	
	
	
	@RequestMapping(value="/adminQnaRepSave", method=RequestMethod.POST)
	public String adminQnaRepSave( @RequestParam("qseq") int qseq,
								@RequestParam("reply") String reply ) {
		as.updateQna( qseq, reply );
		return "redirect:/adminQnaView?qseq=" + qseq;
	}
	
	
	
	@RequestMapping("/adminBannerList")
	public ModelAndView bannerList( ) {
		ModelAndView mav = new ModelAndView();
	
		mav.addObject("bannerList", as.getBannerList() );
		mav.setViewName("admin/banner/bannerList");
		
		return mav;
	}
	
	
	
	
	@RequestMapping("/newBannerWrite")
	public String newBannerWrite() {
		return "admin/banner/writeBanner";
	}
	
	
	@RequestMapping(value="/bannerWrite" )
	public String bannerWrite(  BannerVO bannervo	) {
		if( bannervo.getOrder_seq() == 6 ) bannervo.setUseyn("N");
		else bannervo.setUseyn("Y");
		as.insertBanner( bannervo );
		return "redirect:/adminBannerList";
	}
	
	@RequestMapping("/change_order")
	public String change_order(
			HttpServletRequest request,
			@RequestParam("bseq") int bseq,
			@RequestParam("changeval") int changeval) {
		
		String useyn;
		if( changeval > 5 ) useyn = "N";
		else useyn = "Y";
		
		as.updateSeq( changeval, useyn, bseq );
		
		return "redirect:/adminBannerList";
		
	}
}





















