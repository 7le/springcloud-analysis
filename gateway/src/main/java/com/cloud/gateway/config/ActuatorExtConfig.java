package com.cloud.gateway.config;

import com.cloud.gateway.endpoint.AutoRouteEndpoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 7le
 */
@Configuration
public class ActuatorExtConfig {

    @Bean
    @ConditionalOnEnabledEndpoint
    public AutoRouteEndpoint autoRouteEndpoint(ApplicationContext context, BusProperties bus) {
        return new AutoRouteEndpoint(context,bus.getId());
    }

}