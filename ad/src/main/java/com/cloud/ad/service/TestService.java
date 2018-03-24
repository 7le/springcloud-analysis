package com.cloud.ad.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * rest + ribbon test
 *
 * @author 7le
 * @since v1.0.0
 */
@Service
public class TestService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String hiService(String name) {
        return restTemplate.getForObject("http://spring-cloud-statistic/hi?name="+name,String.class);
    }

    public String error(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
