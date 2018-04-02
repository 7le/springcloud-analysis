package com.cloud.ad.controller;

import com.cloud.ad.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * AdController
 *
 * @author : 7le
 * @since v1.0.0
 */
@RestController
public class AdController {

    private Logger logger= LoggerFactory.getLogger(AdController.class);

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

    @RequestMapping(value = "/retry")
    public String retry() {
        logger.info("request success");
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            logger.info("request error");
        }
        return "Winter is coming";
    }

    /**
     * 响应式test 使用undertow
     */
    @RequestMapping(value = "/")
    public Mono<String> test() {
        logger.info(Thread.currentThread().getName());
        return Mono.create(s -> {
            logger.info(Thread.currentThread().getName());
            s.success(str);
        });
    }
}
