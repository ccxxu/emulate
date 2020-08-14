package com.ce.nw.admin.security;

import com.ce.nw.admin.auth.CustomAuthenticationProvider;
import com.ce.nw.admin.handler.CustomAuthenticationFailureHandler;
import com.ce.nw.admin.handler.CustomAuthenticationSuccessHandler;
import com.ce.nw.admin.handler.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * Created by ccxxu on 2019-08-01.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("bbbbbbbbbbbbbbbbbbbbb");
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
        http.headers().frameOptions().disable();
        http.authorizeRequests()
//                .antMatchers("/getVerifyCode").permitAll()      //如果有允许匿名的url，填在下面
                .antMatchers("/login/invalid", "/css/**", "/ajax/**", "/js/**", "/easyui/**", "/ruoyi.png", "/ruoyi/**", "/favicon.ico", "/font-awesome/**", "/images/**", "/img/**").permitAll()      //如果有允许匿名的url，填在下面
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")        // 设置登陆页
                // 设置登陆成功页
//                .defaultSuccessUrl("/")
                .successHandler(customAuthenticationSuccessHandler)
//                .failureUrl("/login/error")
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()
//                .usernameParameter("username").passwordParameter("password")      //自定义登陆用户名和密码参数，默认为username和password
                .authenticationDetailsSource(authenticationDetailsSource)
                .and()
//                .addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailsService)
                .and().sessionManagement().invalidSessionUrl("/login/invalid")    //session超时配置
                .maximumSessions(1).maxSessionsPreventsLogin(false)
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
                .sessionRegistry(sessionRegistry());


        // 关闭CSRF跨域
        http.csrf().disable();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }

    /**
     * 主动踢出一个用户
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**", "/ajax/**", "/images/**", "/img/**", "/ruoyi/**", "/ruoyi.png", "/favicon.ico", "/font-awesome/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }
}

