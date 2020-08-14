package com.ce.nw.auth.controller;

import com.ce.nw.auth.support.BootSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuit
 * @date  2018/10/9  15:09
 *
 **/
@Controller
public class BaseMainController {

    @Autowired
    private BootSecurityProperties properties;

    @GetMapping("/auth/login")
    public String loginPage(Model model){

        model.addAttribute("loginProcessUrl",properties.getLoginProcessUrl());

        return "base-login";
    }

    @RequestMapping({ "/login", "/login.html" })
    public String dashboard() {
        return "redirect:/";
    }

}
