package com.skt.shms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShmsSampleEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShmsSampleEurekaApplication.class, args);
	}

}
