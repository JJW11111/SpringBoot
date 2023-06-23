package com.ezen.bt.comtroller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.bt.dto.BannerVO;
import com.ezen.bt.service.TestService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


@Controller
public class TestController {

	@Autowired
	TestService ts;
	
	
	@RequestMapping("/")
	public String main(Model model) {
		
		// xml 파일의  getBannerList  기능을 완성하세요 
		
		model.addAttribute("bannerList", ts.getBannerList() );
		return "index";
	}
	
	
	@RequestMapping("/newBannerWrite")
	public String newBannerWrite() {
		return "writeBanner";
	}
	
	
	@RequestMapping(value="/bannerWrite" )
	public String bannerWrite(  BannerVO bannervo	) {
		if( bannervo.getOrder_seq() == 6 ) bannervo.setUseyn("N");
		else bannervo.setUseyn("Y");
		
		// xml 파일의  insertBanner  기능을 완성하세요 
		ts.insertBanner( bannervo );
		return "redirect:";
	}
	
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value="/fileup", method=RequestMethod.POST)
	@ResponseBody  
	public HashMap<String, Object> fileup(Model model, HttpServletRequest request) {
		
		String path = context.getRealPath("/images");
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
	
	
	
	@RequestMapping("/moveBannerPage" )
	public ModelAndView moveBannerPage( ) {
		ModelAndView mav = new ModelAndView();
		
		// xml 파일의  배너 조회  기능을 완성하세요 
		mav.addObject("bannerList", ts.getBannerListFive() );
		mav.setViewName("rollingBanner");
		return mav;
	}
}
