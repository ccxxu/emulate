package com.ce.nw.test.controller;

import com.ce.nw.test.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by huangds on 2017/10/24.
 */
@Controller
public class LoginController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/")
    public String index(Authentication auth) {
        System.out.println("============"+auth.getPrincipal());
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        System.out.println("aaaaaaaaaaaaaaaaaa");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("username");
        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping("/userme")
    public String principal() {

        return "sssssssssssssssssssssssss";
    }
}
