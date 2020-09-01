package com.ce.nw.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
@Controller
public class LoginController {

    @Resource
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private TokenStore tokenStore;

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


    public Map<String, Object> user(@RequestHeader String authorization) {
        Map<String, Object> map = new HashMap<>();

        OAuth2Authentication auth = null;

        try {
            auth = tokenStore.readAuthentication(authorization.split("")[1]);
            if (auth == null) {
                map.put("error", "s");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }

        return map;
    }

}
