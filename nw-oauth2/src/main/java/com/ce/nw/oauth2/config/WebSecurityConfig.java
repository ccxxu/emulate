package com.ce.nw.oauth2.config;

import com.ce.nw.oauth2.handler.CustomLogoutSuccessHandler;
import com.ce.nw.oauth2.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author cxxxu
 * @date 2019-02-11
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    /**
     * 认证管理器配置, 用于信息获取来源（UserDetails）以及密码校验规则（PasswordEncoder）
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 核心过滤器配置, 更多使用ignoring()用来忽略对静态资源的控制
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
    }

    /**
     * 安全过滤器链配置, 自定义安全访问策略
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll()
                .and().authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated();
//        http
//                .authorizeRequests()
//                // /login 和 /oauth/authorize 路径配置为不需要任何身份验证, 其他所有路径必须通过验证
//                .antMatchers("/login", "/oauth/authorize").permitAll()
//                // 其他请求都需要已认证
//                .anyRequest().authenticated()
//                .and()
//                // 使用表单登录
//                .formLogin()
//                // 自定义登录页面地址
//                .loginPage("/login")
//                // 验证表单的地址, 有过滤器 UsernamePasswordAuthenticationFilter 拦截处理
//                .loginProcessingUrl("/login")
//                .and()
//                .logout()
//                .logoutSuccessHandler(customLogoutSuccessHandler)
//                // 无效会话
//                .invalidateHttpSession(true)
//                // 清除身份验证
//                .clearAuthentication(true)
//                .and().csrf().disable().cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
