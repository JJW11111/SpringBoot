package com.ezen.g05;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LombokController {
        
        @RequestMapping("/")
        public String root(){

                return "testForm";
        }

        @RequestMapping("/test1")
        public String test1(@ModelAttribute("memberdto") MemberDto memberdto, Model model) {
                
                System.out.println(memberdto.getId()+" "+memberdto.getName());
                return "test1";
                
        }
}
