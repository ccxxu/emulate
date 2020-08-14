package com.ce.nw.auth.config;

import com.ce.nw.auth.support.BootLoginFailureHandler;
import com.ce.nw.auth.support.BootSecurityProperties;
import com.ce.nw.auth.support.BootUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * @author yuit
 * @date 2018/10/10  11:48
 **/
@Configuration
@Order(2)
@EnableJpaAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BootUserDetailService userDetailService;

    private BootSecurityProperties properties;

    private BootLoginFailureHandler handler;

    public SecurityConfig(BootUserDetailService userDetailService, BootSecurityProperties properties, BootLoginFailureHandler handler) {
        this.userDetailService = userDetailService;
        this.properties = properties;
        this.handler = handler;
    }


    /**
     * 让Security 忽略这些url，不做拦截处理
     *
     * @param
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers
                ("/swagger-ui.html/**", "/webjars/**",
                        "/swagger-resources/**", "/v2/api-docs/**",
                        "/swagger-resources/configuration/ui/**", "/swagger-resources/configuration/security/**",
                        "/images/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // http security 要拦截的url，这里这拦截，oauth2相关和登录登录相关的url，其他的交给资源服务处理
                .requestMatchers()
                .antMatchers( "/oauth/**", properties.getLoginPage(), properties.getLoginProcessUrl())
                .and()
                .authorizeRequests()
                // 自定义页面或处理url是，如果不配置全局允许，浏览器会提示服务器将页面转发多次
                .antMatchers("/auth/login", properties.getLoginProcessUrl())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable().cors();

        // 表单登录

        http.formLogin()
                .failureHandler(handler)
                // 页面
                .loginPage(properties.getLoginPage())
                // 登录处理url
                .loginProcessingUrl(properties.getLoginProcessUrl());

        http.httpBasic().disable();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
