package com.skt.shms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShmsSampleLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShmsSampleLoginApplication.class, args);
	}

}
