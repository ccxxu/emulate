package com.ce.nw.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NwServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NwServerApplication.class, args);
	}

}
