package com.cloud.gateway.config;

import com.cloud.gateway.zuul.CustomRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 7le
 */
@Configuration
public class ZuulConfig {

    @Autowired
    ZuulProperties zuulProperties;
    @Autowired
    ServerProperties server;

    @Bean
    public CustomRouteLocator routeLocator() {
        return new CustomRouteLocator(server.getServlet().getServletPrefix(), zuulProperties);
    }
}
