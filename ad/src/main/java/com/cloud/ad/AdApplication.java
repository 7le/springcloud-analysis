package com.cloud.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 7le
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdApplication.class, args);
	}
}
