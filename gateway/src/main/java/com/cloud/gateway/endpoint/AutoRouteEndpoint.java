package com.cloud.gateway.endpoint;

import com.cloud.gateway.event.AutoRouteEvent;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author 7le
 */
@Endpoint(id = "route-refresh")
public class AutoRouteEndpoint extends AbstractBusEndpoint {

    public AutoRouteEndpoint(ApplicationEventPublisher context, String appId) {
        super(context, appId);
    }

    @WriteOperation
    public void refreshRouteWithDestination(@Selector String destination) {
        publish(new AutoRouteEvent(this, getInstanceId(), destination));
    }

    @WriteOperation
    public void refreshRoute() {
        publish(new AutoRouteEvent(this, getInstanceId(), null));
    }
}
