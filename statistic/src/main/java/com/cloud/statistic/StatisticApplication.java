package com.cloud.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class StatisticApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StatisticApplication.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		app.run();
	}

}
