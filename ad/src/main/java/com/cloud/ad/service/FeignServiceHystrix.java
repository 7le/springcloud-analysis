package com.cloud.ad.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 7le
 * @since v1.0.0
 */
@Slf4j
@Component
public class FeignServiceHystrix implements FallbackFactory<FeignService> {

    @Override
    public FeignService create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        return name -> {
            log.error("调用hiService接口异常：{}", msg);
            return "hi," + name + ",sorry,error!";
        };
    }
}
