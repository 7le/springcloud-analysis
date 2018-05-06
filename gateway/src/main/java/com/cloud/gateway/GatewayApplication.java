package com.cloud.gateway;

import com.cloud.common.annotation.InterceptorScan;
import com.cloud.common.interceptor.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author 7le
 */
@SpringCloudApplication
@EnableZuulProxy
@MapperScan("com.cloud.common.dao")
@InterceptorScan("com.cloud.common.interceptor")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
