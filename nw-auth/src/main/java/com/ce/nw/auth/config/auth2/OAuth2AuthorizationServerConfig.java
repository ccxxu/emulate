package com.ce.nw.auth.config.auth2;

import com.ce.nw.auth.enhancer.CustomTokenEnhancer;
import com.ce.nw.auth.filter.BootBasicAuthenticationFilter;
import com.ce.nw.auth.support.oauth2.BootOAuth2WebResponseExceptionTranslator;
import com.ce.nw.auth.support.BootUserDetailService;
import com.ce.nw.auth.support.oauth2.BootClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @Author :ccx
 * @date :2019-09-28
 *
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;

    private ClientDetailsService clientDetailsService;

    private TokenStore tokenStore;

    private DataSource dataSource;

    private JwtAccessTokenConverter converter;

    private AuthenticationEntryPoint authenticationEntryPoint;

    private BootOAuth2WebResponseExceptionTranslator bootWebResponseExceptionTranslator;

    private BootBasicAuthenticationFilter filter;

    private UserDetailsService userDetailsService;

    @Autowired(required = false)
    public OAuth2AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                           BootClientDetailsService clientDetailsService,
                                           TokenStore tokenStore, JwtAccessTokenConverter converter,
                                           DataSource dataSource,
                                           AuthenticationEntryPoint authenticationEntryPoint,
                                           BootOAuth2WebResponseExceptionTranslator bootWebResponseExceptionTranslator,
                                           BootBasicAuthenticationFilter filter, BootUserDetailService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.clientDetailsService = clientDetailsService;
//        this.tokenStore = tokenStore;
        this.tokenStore = new JdbcTokenStore(dataSource);
        this.converter = converter;
        this.dataSource = dataSource;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.bootWebResponseExceptionTranslator = bootWebResponseExceptionTranslator;
        this.filter = filter;
        this.userDetailsService = userDetailsService;
    }

    public OAuth2AuthorizationServerConfig() {
        super();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单登录
        security.allowFormAuthenticationForClients();
        // 加载client的service
        filter.setClientDetailsService(clientDetailsService);
        // 自定义异常处理端口
        security.authenticationEntryPoint(authenticationEntryPoint);
        // 客户端认证之前的过滤器
        security.addTokenEndpointAuthenticationFilter(filter);

        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置加载客户端的service
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), this.converter));
        endpoints
                // token 存储方式
                .tokenStore(tokenStore).approvalStore(approvalStore())
                .tokenEnhancer(chain)
                .authenticationManager(authenticationManager)
                // 不配置会导致token无法刷新
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET);

        // 判断当前是否使用jwt
        if(!(tokenStore instanceof RedisTokenStore) && this.converter!=null){
            endpoints.accessTokenConverter(converter);
        }

        // 处理 ExceptionTranslationFilter 抛出的异常
        endpoints.exceptionTranslator(bootWebResponseExceptionTranslator);

//        endpoints.pathMapping("/oauth/confirm_access","/custom/confirm_access");
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public ApprovalStore approvalStore() {
        TokenApprovalStore tokenApprovalStore = new TokenApprovalStore();
        tokenApprovalStore.setTokenStore(this.tokenStore);
        return tokenApprovalStore;
    }
}
