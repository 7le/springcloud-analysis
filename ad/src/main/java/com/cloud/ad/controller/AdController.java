package com.cloud.ad.controller;

import com.cloud.ad.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * AdController
 *
 * @author : 7le
 * @since v1.0.0
 */
@RestController
public class AdController {

    @Autowired
    TestService testService;

    @Value("${7le}")
    private String str;

    @RequestMapping("/status")
    public String health() {
        return "Winter is coming";
    }

    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return testService.hiService(name);
    }

    /**
     * 响应式test 使用undertow
     */
    @RequestMapping(value = "/")
    public Mono<String> test() {
        System.out.println(Thread.currentThread().getName());
        return Mono.create(s -> {
            System.out.println(Thread.currentThread().getName());
            s.success(str);
        });
    }
}
