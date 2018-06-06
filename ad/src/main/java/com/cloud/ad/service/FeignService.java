package com.cloud.ad.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 7le
 * @since v1.0.0
 */
@FeignClient(value = "spring-cloud-statistic",fallback = FeignServiceHystrix.class)
public interface FeignService {

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String hiService(@RequestParam("name") String name);
}
