package com.ce.nw.test.controller;

import com.ce.nw.test.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("============"+auth);
        if(!(auth instanceof OAuth2Authentication)){
            return "";
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        //取出用户身份信息
        String principal = userAuthentication.getName();

        //取出用户权限
        List<String> authorities = new ArrayList<>();
        //从userAuthentication取出权限，放在authorities
        userAuthentication.getAuthorities().stream().forEach(c->authorities.add(((GrantedAuthority) c).getAuthority()));

        System.out.println("p="+principal);

        return "index";
    }

    /*
    @RequestMapping("/login1")
    public String login(){
        System.out.println("aaaaaaaaaaaaaaaaaa");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("username");
        return "redirect:/login";
    }
*/
    @ResponseBody
    @GetMapping("/userme")
    public String principal() {

        return "sssssssssssssssssssssssss";
    }
}
