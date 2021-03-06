package com.cloud.gateway;

import com.cloud.common.annotation.InterceptorScan;
import com.cloud.common.interceptor.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 7le
 */
@SpringCloudApplication
@EnableZuulProxy
@MapperScan("com.cloud.common.dao")
@InterceptorScan("com.cloud.common.interceptor")
@EnableTransactionManagement
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run();
    }
}
