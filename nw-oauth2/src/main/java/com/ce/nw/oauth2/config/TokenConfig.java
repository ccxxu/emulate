package com.ce.nw.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author ccx
 * @description 令牌管理
 * @date 2020-08-11
 */
@Configuration
public class TokenConfig {

    private String SIGNING_KEY = "ciecc2020";

    @Bean
    public TokenStore tokenStore() {
        // JWT令牌存储方案
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(SIGNING_KEY); //对称密钥, 资源服务器使用该密钥来验证
        return jwtAccessTokenConverter;
    }

    /*
    @Bean
    public TokenStore tokenStore() {
        // 使用内存存储令牌(普通令牌)
        return new InMemoryTokenStore();
    }
    */

}
