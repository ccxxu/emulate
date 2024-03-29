package com.ce.nw.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.ce.nw")
@EnableJpaRepositories(basePackages = {"com.ce.nw.common.dao"})
@EntityScan(basePackages = {"com.ce.nw.common.model"})
public class NwOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(NwOauth2Application.class, args);
	}

}
