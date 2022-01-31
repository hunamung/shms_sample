package com.skt.shms.web;

import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/info")
    public ResponseEntity<?> info(Principal principal
            ,HttpServletRequest request, @RequestParam HashMap<String,String> paramMap) {
        System.out.println("user info~~~!!");
        return ResponseEntity.ok(principal);
    }
    
    @GetMapping(value="/info22")
    public String info22() {
        System.out.println("user info22~~~!!");
        return "user info22";
    }
    
    @GetMapping("/info33")
    public ResponseEntity<?> info33(Principal principal
            ,HttpServletRequest request, @RequestParam HashMap<String,String> paramMap) {
        System.out.println("user info33~~~!!");
        return ResponseEntity.ok(principal);
    }
    
    @GetMapping(value="/profile")
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView("userProfile");
        return mav;
    }
}
