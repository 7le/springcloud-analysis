package com.cloud.statistic.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Test
 *
 * @author 7le
 * @since v1.0.0
 */
@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public Mono<Object> home(@RequestParam String name) {
        return Mono.just("hi "+name+",i am from port:" +port);
    }
}
