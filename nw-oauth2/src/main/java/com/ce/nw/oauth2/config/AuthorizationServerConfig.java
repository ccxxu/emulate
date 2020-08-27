package com.ce.nw.oauth2.config;

import com.ce.nw.oauth2.enhancer.CustomTokenEnhancer;
import com.ce.nw.oauth2.filter.MyBasicAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author ccxxu
 * @date 2019-02-11
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore tokenStore;

//    @Autowired
//    private MyClientDetailsService clientDetailsService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

//    @Autowired
//    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private MyBasicAuthenticationFilter filter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用来配置令牌端点的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.allowFormAuthenticationForClients();
        // 允许表单登录
//        security.allowFormAuthenticationForClients();
//        // 加载client的service
//        filter.setClientDetailsService(clientDetailsService);
        // 自定义异常处理端口
//        security.authenticationEntryPoint(authenticationEntryPoint);
        // 客户端认证之前的过滤器
//        security.addTokenEndpointAuthenticationFilter(filter);
        //开启 /oauth/check_token
//        security.tokenKeyAccess("isAuthenticated()");
        security
                // 开启 /oauth/check_token
                .tokenKeyAccess("permitAll()")
                // 开启 /oauth/token_key
//                .checkTokenAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();

    }

    //将客户端信息存储到数据库
    @Bean
    public ClientDetailsService clientDetailsService() {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService）,客户端详细信息在这里进行初始化,
     * 可以把客户端详情信息写死在这里
     * 也可以通过数据库存储调取详情信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);
        clients.withClientDetails(this.clientDetailsService);
    }

    /**
     * 用来配置令牌（token）的访问端点和令牌服务（tokenservices）
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
/*
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter));
//        chain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        endpoints.accessTokenConverter(accessTokenConverter);
*/

//        this.tokenStore = new JdbcTokenStore(dataSource);
//        endpoints.tokenStore(jwtTokenStore()).authenticationManager(authenticationManager);
//        endpoints.tokenStore(new JdbcTokenStore(dataSource))
//                .tokenEnhancer(chain)
//                .authenticationManager(authenticationManager)
//                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET);
//        endpoints.tokenServices(defaultTokenServices());
//        endpoints.pathMapping("/oauth/confirm_access", "/custom/confirm_access");


//        endpoints
//                .authenticationManager(authenticationManager)//认证管理器
//                .authorizationCodeServices(authorizationCodeServices)//授权码服务
//                .tokenServices(tokenService())//令牌管理服务
//                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET);

        endpoints
                .authenticationManager(authenticationManager)//认证管理器
                .authorizationCodeServices(authorizationCodeServices)//授权码服务
//                .tokenStore(new JdbcTokenStore(dataSource))
//                .tokenEnhancer(chain)
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET);
    }

    //令牌管理服务
    @Primary
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);//客户端详情服务
        service.setSupportRefreshToken(true);//支持刷新令牌
        service.setReuseRefreshToken(false);//
        service.setTokenStore(new JdbcTokenStore(dataSource));//令牌存储策略
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter));
//        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);
        service.setAccessTokenValiditySeconds(15); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(60); // 刷新令牌默认有效期3天
        return service;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);//设置授权码模式的授权码如何存取
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

}
