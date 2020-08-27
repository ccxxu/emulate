package com.ce.nw.oauth2.filter;

import com.alibaba.fastjson.JSONObject;
import com.ce.nw.oauth2.domain.BaseResponse;
import com.ce.nw.oauth2.domain.HttpResponse;
import com.ce.nw.oauth2.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 燕园夜雨
 * @date 2020-08-19 09：24
 * @desc 认证授权码携带的令牌 *
 */
@Component
public class MyBasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ClientDetailsService clientDetailsService;

//    @Autowired
//    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("MyBasicAuthenticationFilter=="+request.getRequestURI()+",grant_type="+request.getParameter("grant_type")+", username="+authentication);

        if (!request.getRequestURI().equals("/oauth/token") ||
                !request.getParameter("grant_type").equals("authorization_code")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("开始授权码认证.....");
        String[] clientDetails = this.isHasClientDetails(request);
        if (clientDetails == null) {
            BaseResponse bs = HttpResponse.baseResponse(HttpStatus.UNAUTHORIZED.value(), "请求中未包含客户端信息");
            HttpUtils.writerError(bs, response);
            return;
        }
        this.handle(request,response,clientDetails,filterChain);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, String[] clientDetails,FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            filterChain.doFilter(request,response);
            return;
        }
        ClientDetails details = this.clientDetailsService.loadClientByClientId(clientDetails[0]);

//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(details.getClientId(), details.getClientSecret(), details.getAuthorities());
//             SecurityContextHolder.getContext().setAuthentication(token);
//
//        if (details == null){
//            throw new UnapprovedClientAuthenticationException("clientId 不存在"+clientId);
//            //判断  方言  是否一致
//        }else if (!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
//            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配"+clientId);
//        }
        //密码授权 模式, 组建 authentication
//        Map<String, String> rp = new HashMap<>();
//        TokenRequest tokenRequest = new TokenRequest(rp, details.getClientId(), details.getScope(), "authorization_code");
//
//        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(details);
//        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
//        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
//        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(token);
//        System.out.println("BasicFilter生成的令牌是:"+jsonObject.toString());
//        token.getValue();
//        String json = EncryptUtil.decodeUTF8StringBase64(token);
        filterChain.doFilter(request,response);
    }

    /**
     * 判断请求头中是否包含client信息，不包含返回null
     */
    private String[] isHasClientDetails(HttpServletRequest request) {
        String[] params = null;
        //sd
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String basic = header.substring(0, 5);
            if (basic.toLowerCase().contains("basic")) {
                String tmp = header.substring(6);
                System.out.println(tmp);
                String defaultClientDetails = new String(Base64.getDecoder().decode(tmp));
                String[] clientArrays = defaultClientDetails.split(":");
                if (clientArrays.length != 2) {
                    return params;
                } else {
                    params = clientArrays;
                }
            }
        }

        String id = request.getParameter("client_id");
        String secret = request.getParameter("client_secret");

        if (header == null && id != null) {
            params = new String[]{id, secret};
        }


        return params;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

}
