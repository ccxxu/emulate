package com.ce.nw.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author ccx
 * @description 资源管理配置
 * @date 2020-08-18
 */

@Configuration
@EnableResourceServer
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

//    public static final String RESOURCE_ID = "test";
//
//    @Autowired
//    private TokenStore tokenStore;

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID)//资源 id
//                .tokenStore(tokenStore)
////                .tokenServices(tokenService())//验证令牌的服务
//                .stateless(true);
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/**").access("#oauth2.hasScope('ROLE_ADMIN')")
//                .and()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/member/**")
                .authenticated();


    }

    //资源服务令牌解析服务
//    @Bean
//    public ResourceServerTokenServices tokenService() {
//        //使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
//        RemoteTokenServices service=new RemoteTokenServices();
//        service.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//        service.setClientId("UserManagement");
//        service.setClientSecret("user123");
//        return service;
//    }

}
