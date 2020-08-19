package com.ce.nw.oauth2.enhancer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ccx
 * @description 生成令牌
 * @date 2020-08-19
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken token, OAuth2Authentication authentication) {
        System.out.println("调用生成令牌方法......");
        Map<String, Object> additionInfo = new HashMap<>();
        additionInfo.put("principal", authentication.getPrincipal());
        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionInfo);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(token);
        System.out.println("Enhancer生成的令牌是:"+jsonObject.toString());
        return token;
    }

}
