package com.cloud.gateway.config;

import com.cloud.gateway.event.AutoRouteEvent;
import com.cloud.gateway.service.RouteRefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author 7le
 */
@Configuration
@RemoteApplicationEventScan({"com.cloud.gateway.event"})
public class BusConfiguration {

    @Autowired
    RouteRefreshService routeRefreshService;

    @EventListener(classes = AutoRouteEvent.class)
    public void routeRefresh() {
        routeRefreshService.routeRefresh();
    }
}