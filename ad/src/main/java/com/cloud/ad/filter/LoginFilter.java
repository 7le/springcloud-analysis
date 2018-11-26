package com.cloud.ad.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * webflux 过滤器
 *
 * @author 7le
 * @version 1.0.0
 */
@Component
public class LoginFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        if (!serverWebExchange.getRequest().getHeaders().containsKey("token")) {
            serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
        return webFilterChain.filter(serverWebExchange);
    }
}