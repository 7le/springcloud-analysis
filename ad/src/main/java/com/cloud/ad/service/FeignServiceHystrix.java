package com.cloud.ad.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 7le
 * @since v1.0.0
 */
@Component
public class FeignServiceHystrix implements FeignService {
    @Override
    public String hiService(@RequestParam("name") String name) {
        return "hi," + name + ",sorry,error!";
    }
}
