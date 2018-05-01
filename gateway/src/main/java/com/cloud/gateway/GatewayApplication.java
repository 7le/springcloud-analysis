package com.cloud.gateway;

import com.cloud.common.interceptor.PerformanceInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author 7le
 */
@SpringCloudApplication
@EnableZuulProxy
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

}
