package com.ce.nw.oauth2.enhancer;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ccx
 * @description
 * @date
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> additionInfo = new HashMap<>();
        additionInfo.put("username", authentication.getPrincipal());
        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionInfo);
        return token;
    }

}
