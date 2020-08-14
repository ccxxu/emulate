package com.ce.nw.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.ce.nw")
@EnableJpaRepositories("com.ce.nw.common.dao")
@EntityScan("com.ce.nw.common.model")
@EnableSwagger2
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//		,RedisAutoConfiguration.class,RedisRepositoriesAutoConfiguration.class
})
public class NwDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwDevApplication.class, args);
	}

}
