package com.ce.nw.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author ccx
 * @description 配置资源访问策略
 * @date 2020-08-18
 */
@Configuration
public class ResourceServerConfig {

    public static final String RESOURCE_ID = "res2020";

    //uaa资源服务配置
    @Configuration
    @EnableResourceServer
    public class UAAServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources.tokenStore(tokenStore).resourceId(RESOURCE_ID)
                    .stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/ca/**").permitAll();
        }
    }


//    //order资源
//    //uaa资源服务配置
//    @Configuration
//    @EnableResourceServer
//    public class OrderServerConfig extends ResourceServerConfigurerAdapter {
//
//        @Autowired
//        private TokenStore tokenStore;
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources){
//            resources
//                    .tokenStore(tokenStore)
//                    .resourceId(RESOURCE_ID)
//                    .stateless(true);
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .antMatchers("/test/**").permitAll();
////                    .access("#oauth2.hasScope('ROLE_API')");
//        }
//    }

}
