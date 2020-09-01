package com.ce.nw.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


//@EnableResourceServer
@EnableOAuth2Sso
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class NwClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwClientApplication.class, args);
	}

	/*
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
												 OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, oauth2ClientContext);
	}
	*/

}
