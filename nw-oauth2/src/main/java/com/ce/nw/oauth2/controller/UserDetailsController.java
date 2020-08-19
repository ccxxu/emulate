package com.ce.nw.oauth2.controller;

import com.alibaba.fastjson.JSON;
import com.ce.nw.common.util.EncryptUtil;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 燕园夜雨
 * @date 2020-08-19
 * 根据令牌信息获取用户信息
 */
//@Controller
public class UserDetailsController {

//    @Resource
    private UserInfoTokenServices userInfoTokenServices;

    /**
     * 根据token获取用户信息
     * @param accessToken
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/token/{accesstoken}", method = RequestMethod.GET)
    public String getUserByToken(@PathVariable(value = "accessToken", required = true) String accessToken, @RequestHeader(value = "userId", required = true) Long userId) throws Exception {
        OAuth2Authentication auth = this.userInfoTokenServices.loadAuthentication(accessToken);
        Authentication userAuthentication = auth.getUserAuthentication();
        //取出用户身份信息
        String principal = userAuthentication.getName();
        //取出用户权限
        List<String> authorities = new ArrayList<>();
        //从userAuthentication取出权限，放在authorities
        userAuthentication.getAuthorities().stream().forEach(c->authorities.add(c.getAuthority()));
        OAuth2Request oAuth2Request = auth.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String,Object> jsonToken = new HashMap<>(requestParameters);
        if(userAuthentication!=null){
            jsonToken.put("principal",principal);
            jsonToken.put("authorities",authorities);
        }
        return EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken));
    }

}
