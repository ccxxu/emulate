package com.ce.nw.dev.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ccxxu on 2019-08-06.
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        String username = "";
        if (principal instanceof User) {
            username = ((User)principal).getUsername();
        }
        log.info("退出成功，用户名:{}", username);
        response.sendRedirect("/login");
    }
}
