package com.cloud.ad.controller;

import com.cloud.ad.bean.ResultBean;
import com.cloud.ad.bean.ValidDTO;
import com.cloud.ad.service.FeignService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * AdController
 *
 * @author : 7le
 * @since v1.0.0
 */
@Slf4j
@RestController
@RefreshScope
public class AdController {

    private Logger logger = LoggerFactory.getLogger(AdController.class);

    @Autowired
    FeignService feignService;

    @Value("${7le}")
    private String str;

    @RequestMapping(value = "/hi")
    public Mono<Object> hi(@RequestParam String name) {
        return Mono.just(feignService.hiService(name));
    }

    @RequestMapping(value = "/retry")
    public String retry() {
        log.info("request success");
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            log.info("request error");
        }
        return "Winter is coming";
    }

    /**
     * 响应式test 使用undertow
     */
    @RequestMapping(value = "/")
    public Mono<Object> test() {
        log.info(Thread.currentThread().getName());
        return Mono.create(s -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName());
            s.success(ResultBean.success(str));
        }).subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = "/valid", produces = "application/json; charset=utf-8")
    public Mono<Object> valid(@RequestBody @Valid ValidDTO dto) {
        return Mono.create(s -> log.info("Name :{}, Type:{} ", dto.getName(), dto.getType()))
                .subscribeOn(Schedulers.elastic());
    }
}
