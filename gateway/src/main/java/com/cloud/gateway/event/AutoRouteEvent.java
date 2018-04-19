package com.cloud.gateway.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author 7le
 */
public class AutoRouteEvent extends RemoteApplicationEvent {

    protected AutoRouteEvent() {
    }

    protected AutoRouteEvent(Object source, String originService) {
        super(source, originService, null);
    }

    public AutoRouteEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }

}