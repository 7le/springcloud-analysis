package com.cloud.gateway.filter;

import com.cloud.common.util.HttpUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class LoggerFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();
        Map<String, String> params = HttpUtils.getParams(request);
        String paramsStr = params.toString();//请求的参数
        long startTime = (long) context.get("startTime");
        Throwable throwable = context.getThrowable();
        request.getRequestURI();
        HttpUtils.getClientIp(request);
        context.getResponseStatusCode();
        long duration = System.currentTimeMillis() - startTime;
        logger.info("method: " + method + " params: " + paramsStr + " duration: " + duration + " throwable: " + throwable);
        return null;
    }

}
