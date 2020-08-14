package com.ce.nw.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author yuit
 */
@SpringBootApplication
@ComponentScan("com.ce.nw")
@EnableJpaRepositories(basePackages = {"com.ce.nw.common.dao", "com.ce.nw.auth.mapper"})
@EntityScan(basePackages = {"com.ce.nw.common.model", "com.ce.nw.auth.entity"})
public class NwAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(NwAuthApplication.class, args);
    }

}
