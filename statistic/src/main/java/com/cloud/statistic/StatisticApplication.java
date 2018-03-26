package com.cloud.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringCloudApplication
public class StatisticApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticApplication.class, args);
	}

}
