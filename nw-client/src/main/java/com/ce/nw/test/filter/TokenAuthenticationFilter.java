package com.ce.nw.test.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ccx
 * @description 过滤器：转换令牌格式
 * @date 2020-08-11
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("test uri = "+request.getRequestURI());

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth!=null) {
//            System.out.println("============" + auth);
//            if (!(auth instanceof OAuth2Authentication)) {
//                response.sendRedirect(request.getContextPath() + "/logout");
//                return;
//            }
//            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
//            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
//            //取出用户身份信息
//            String principal = userAuthentication.getName();
//            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
//            System.out.println(details.getTokenValue());
//
//            //取出用户权限
//            List<String> authorities = new ArrayList<>();
//            //从userAuthentication取出权限，放在authorities
//            userAuthentication.getAuthorities().stream().forEach(c -> authorities.add(((GrantedAuthority) c).getAuthority()));
//            System.out.println("p=" + principal);
//            String url = "http://localhost:8080/oauth/check_token?token=" + details.getTokenValue();
//            System.out.println(url);
//
//
//            String result = restTemplate.getForObject(url, String.class);
//            System.out.println(result);
//
//        }

        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            System.out.println("token: "+csrf.getToken());
            Cookie cookie = new Cookie("XSRF-TOKEN", csrf.getToken());
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        filterChain.doFilter(request, response);
    }

}
