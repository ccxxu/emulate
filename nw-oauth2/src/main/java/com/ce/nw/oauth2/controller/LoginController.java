package com.ce.nw.oauth2.controller;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
@Controller
public class LoginController {

    @Resource
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, @RequestParam("token")String accessToken){
        session.removeAttribute("username");
        consumerTokenServices.revokeToken(accessToken);
        return "redirect:/login";
    }

}
