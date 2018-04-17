package com.cloud.gateway.controller;

import com.cloud.gateway.service.RouteRefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @author : 7le
 * @since v1.0.0
 */
@RestController
public class BaseController {

    @Autowired
    RouteRefreshService routeRefreshService;

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("/route-refresh")
    public String refreshRoute(){
        routeRefreshService.routeRefresh();
        return "route-refresh";
    }

    @RequestMapping("/watchNowRoute")
    public String watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return "watchNowRoute";
    }

 }
