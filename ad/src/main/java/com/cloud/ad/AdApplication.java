package com.cloud.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 7le
 */
@SpringCloudApplication
@EnableHystrix
@EnableFeignClients
public class AdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdApplication.class, args);
	}
}
