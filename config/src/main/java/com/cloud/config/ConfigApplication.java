package com.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringCloudApplication
@EnableConfigServer
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ConfigApplication.class);
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.run();
	}
}
