package com.cloud.ad;

import com.cloud.common.annotation.AdviceScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 7le
 */
@SpringCloudApplication
@EnableHystrix
@EnableFeignClients
@AdviceScan("com.cloud.kcommon.advice")
public class AdApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AdApplication.class);
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.run();
	}
}
