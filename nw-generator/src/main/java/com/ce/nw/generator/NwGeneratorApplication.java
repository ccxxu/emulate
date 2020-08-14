package com.ce.nw.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ce.nw.generator.dao")
public class NwGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwGeneratorApplication.class, args);
	}

}
