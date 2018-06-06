package com.cloud.ad.controller;

import com.cloud.ad.bean.ResultBean;
import com.cloud.ad.service.FeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * AdController
 *
 * @author : 7le
 * @since v1.0.0
 */
@RestController
@RefreshScope
public class AdController {

    private Logger logger= LoggerFactory.getLogger(AdController.class);

    @Autowired
    FeignService feignService;

    @Value("${7le}")
    private String str;

    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return feignService.hiService(name);
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
    public Mono<Object> test() {
        logger.info(Thread.currentThread().getName());
        return Mono.create(s -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info(Thread.currentThread().getName());
            s.success(ResultBean.SUCCESS);
        }).subscribeOn(Schedulers.elastic());
    }
}
