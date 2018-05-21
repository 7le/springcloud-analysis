package com.cloud.gateway.controller;

import com.cloud.gateway.service.RouteRefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author : 7le
 * @since v1.0.0
 */
@RestController
public class BaseController {

    @Autowired
    RouteRefreshService routeRefreshService;

    @RequestMapping("/route-refresh")
    public String refreshRoute(){
        routeRefreshService.routeRefresh();
        return "route-refresh";
    }
}
