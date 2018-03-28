package com.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * 异常过滤器
 *
 * @author 7le
 */
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        return null;
    }
}